package me.melonade.collections.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import me.melonade.collections.conf.AppConfig;
import me.melonade.collections.exceptions.CollectionException;
import me.melonade.collections.models.CollectionOrganizerModel;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PreDestroy;
import java.util.*;

/**
 * Created by damon on 8/3/2016.
 * Handles all MongoDB Related tasks
 */

@Component
public class MongoDB {
    private static MongoClient client;
    private static MongoDatabase database;
    private static MongoCollection collectionOrganizer;

    private static final Logger logger = LoggerFactory.getLogger(MongoDB.class.getSimpleName());

    @Autowired
    private MongoDB(AppConfig properties) {
        client = new MongoClient(properties.getMongoServer());
        database = client.getDatabase(properties.getMongoDatabase());
        collectionOrganizer = database.getCollection("collectionOrganizer");

        // To combat the silliness that is not know how to shut down a java application the correct way
        collectionOrganizer.updateMany(new Document(), new Document("$set", new Document("active_flag", false)));
    }

    public MongoCollection getCollection(String collectionName) {
        return database.getCollection(collectionName);
    }

    private String findCollectionIdByKeywords(List<String> keywords) {
        String collectionId = null;
        FindIterable<Document> organizer = collectionOrganizer.find(new Document("keywords", keywords));
        for (Document doc : organizer) {
            if (keywords.equals(doc.get("keywords")))
                collectionId = doc.get("_id").toString();
        }
        return collectionId;
    }

    public List<String> findKeywordsByCollectionId(String collectionId) throws CollectionException {
        FindIterable<Document> organizer = collectionOrganizer.find(new Document("_id", new ObjectId(collectionId)));
        for (Document doc : organizer) {
            logger.debug(doc.get("_id") + " vs " + collectionId);
            if (collectionId.equals(doc.get("_id").toString())) {
                logger.debug(doc.get("keywords").toString());
                return (List<String>) doc.get("keywords");
            }
        }
        throw new CollectionException("Invalid Collection");
    }

    public List<CollectionOrganizerModel> listCollectionOrganizer() {
        FindIterable<Document> organizer = collectionOrganizer.find();

        List<CollectionOrganizerModel> modelList = new ArrayList<>();
        for (Document doc : organizer) {
            CollectionOrganizerModel model = parseCollectionOrganizer(doc);
            modelList.add(model);
        }
        return modelList;
    }

    private CollectionOrganizerModel parseCollectionOrganizer(Document doc) {
        CollectionOrganizerModel model = new CollectionOrganizerModel();
        List<String> keywords = new ArrayList<>();
        for (String word : (List<String>) doc.get("keywords"))
            keywords.add(StringUtils.capitalize(word));

        model.set_id(doc.get("_id").toString());
        model.setKeywords(keywords);
        model.setDesc(doc.get("desc").toString());
        model.setActiveFlag((Boolean) doc.get("active_flag"));

        return model;
    }

    // Adds a configuration to the collectionOrganizer collection
    public void newStream(String[] keywords, String desc) throws CollectionException {
        //Prep list of keywords to lowercase and ordered ?? definitely a quicker way to do this
        List<String> orderedKeywords = Arrays.asList(keywords);
        ListIterator<String> keyword = orderedKeywords.listIterator();
        while (keyword.hasNext()) {
            keyword.set(keyword.next().toLowerCase());
        }
        Collections.sort(orderedKeywords);

        //If keyword combination exists, throw error. Otherwise, create a collection for those keywords
        if (findCollectionIdByKeywords(orderedKeywords) == null)
            collectionOrganizer.insertOne(new Document()
                    .append("keywords", orderedKeywords)
                    .append("desc", desc)
                    .append("active_flag", false));
        else
            throw new CollectionException("Collection Already Exists");
    }

    // Set indicator that stream is running On
    public void setStreamOn(String collectionId) {
        collectionOrganizer.updateOne(new Document("_id", new ObjectId(collectionId)), new Document("$set", new Document("active_flag", true)));
    }

    // Set indicator that stream is running Off
    @PreDestroy
    public void setStreamOff() {
        collectionOrganizer.updateMany(new Document(), new Document("$set", new Document("active_flag", false)));
    }

    // Set indicator that stream is running Off
    public void setStreamOff(String collectionId) {
        collectionOrganizer.updateOne(new Document("_id", new ObjectId(collectionId)), new Document("$set", new Document("active_flag", false)));
    }

    // Drops collection and record in collectionOrganizer
    public void deleteStream(String collectionId) {
        getCollection(collectionId).drop();
        collectionOrganizer.deleteOne(new Document("_id", new ObjectId(collectionId)));
    }
}
