package me.melonade.collections.twitter;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.UpdateOptions;
import me.melonade.collections.conf.AppConfig;
import me.melonade.collections.exceptions.CollectionException;
import me.melonade.collections.exceptions.TwitterStreamException;
import me.melonade.collections.mongo.MongoDB;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.*;

/**
 * Created by damon on 7/18/2016.
 * Contains logic behind managing StreamListeners on the TwitterStream
 */

@Service
public class TwitterStream {

    @Autowired
    private MongoDB mongoDB;
    private static Map<String, StatusListener> listeners = new HashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(TwitterStream.class.getSimpleName());
    private static twitter4j.TwitterStream twitterStream = null;
    private static FilterQuery filterQuery = new FilterQuery();

    //Starts a TwitterStream, should happen only once and stay open for the duration of the run
    @Autowired
    public TwitterStream(AppConfig properties) throws TwitterException {
        //Authentication
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setOAuthConsumerKey(properties.getConsumerKey())
                .setOAuthConsumerSecret(properties.getConsumerSecret())
                .setOAuthAccessToken(properties.getAccessToken())
                .setOAuthAccessTokenSecret(properties.getAccessTokenSecret())
                .setJSONStoreEnabled(true);

        Configuration conf = configurationBuilder.build();

        Twitter twitter = new TwitterFactory(conf).getInstance();
        User user = twitter.verifyCredentials();

        //Make TwitterStream
        twitterStream = new TwitterStreamFactory(conf).getInstance();

    }

    private StatusListener findStatusListenerByCollectionId(String collectionId) {
        return listeners.get(collectionId);
    }

    public void shutdown(String collectionId) throws TwitterStreamException, CollectionException {
        if (listeners.get(collectionId) != null) {
            twitterStream.removeListener(findStatusListenerByCollectionId(collectionId));
            listeners.remove(collectionId);

            // Adjust twitterStream filter
            if (listeners.size() != 0) twitterStream.filter(generateFilterQuery());
        } else
            throw new TwitterStreamException("Collection is not running");
    }

    public void shutdown() {
        twitterStream.clearListeners();
        listeners.clear();
    }

    public void addListener(String collectionId) throws CollectionException {
        //Connect to Mongo
        final MongoCollection collection = mongoDB.getCollection(collectionId);
        collection.createIndex(new Document("text","text").append("createDate",1));
        //Create StatusListener
        StatusListener listener = new StatusListener() {
            @Override
            public void onStatus(Status status) {
                //Create document
                Document document = new Document();

                //Basic Info
                document.append("_id", status.getId())
                        .append("text", status.getText())
                        .append("createDate", status.getCreatedAt())
                        .append("favoriteCount", status.getFavoriteCount())
                        .append("retweetCount", status.getRetweetCount())
                        .append("retweetStatus", status.isRetweeted());
                //System.out.println(status);

                //geoLocation
                if (status.getGeoLocation() != null)
                    document.append("geo",
                            new Document()
                                    .append("lat", status.getGeoLocation().getLatitude())
                                    .append("lon", status.getGeoLocation().getLongitude())
                    );
                //hashTags
                if (status.getHashtagEntities().length > 0) {
                    List<String> hashtags = new ArrayList<>();
                    for (HashtagEntity hash : status.getHashtagEntities()) {
                        hashtags.add(hash.getText());
                    }
                    document.append("hashtags", hashtags);
                }

                //Add document to collection using upsert
                collection.replaceOne(
                        new Document().append("_id", status.getId()),
                        document,
                        new UpdateOptions().upsert(true)
                );
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
                logger.info("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
            }

            @Override
            public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
                logger.info("Got track limitation notice:" + numberOfLimitedStatuses);
            }

            public void onScrubGeo(long userId, long upToStatusId) {
                logger.info("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
            }

            public void onStallWarning(StallWarning warning) {
                logger.error("Got stall warning:" + warning);
            }

            public void onException(Exception e) {
                e.printStackTrace();
            }
        };

        // Add thread to twitterStream
        twitterStream.addListener(listener);

        // Track running thread in Map<String,StatusListener> listeners
        listeners.put(collectionId, listener);

        // Adjust twitterStream filter
        twitterStream.filter(generateFilterQuery());
    }

    private FilterQuery generateFilterQuery() throws CollectionException {
        HashSet<String> keywords = new HashSet<>();
        for (String collectionId : listeners.keySet()) {
            keywords.addAll(mongoDB.findKeywordsByCollectionId(collectionId));
        }
        filterQuery.track(keywords.toArray(new String[0]))
                .language("en");  //If ever adding more languages, change this
        return filterQuery;
    }


}
