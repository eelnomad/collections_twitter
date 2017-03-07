package me.melonade.visualSeries.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by damon on 7/19/2016.
 */

@Component
public class AppConfig {

    @Value("${" + Statics.CONSUMER_KEY + ":}")
    private String consumerKey;

    @Value("${" + Statics.CONSUMER_SECRET + ":}")
    private String consumerSecret;

    @Value("${" + Statics.ACCESS_TOKEN + ":}")
    private String accessToken;

    @Value("${" + Statics.ACCESS_TOKEN_SECRET + ":}")
    private String accessTokenSecret;

    @Value("${" + Statics.MONGO_SERVER + ":}")
    private String mongoServer;

    @Value("${" + Statics.MONGO_DATABASE + ":}")
    private String mongoDatabase;

    public String getConsumerKey() {
        return consumerKey;
    }

    public String getConsumerSecret() {
        return consumerSecret;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getAccessTokenSecret() {
        return accessTokenSecret;
    }

    public String getMongoServer() {
        return mongoServer;
    }

    public String getMongoDatabase() {
        return mongoDatabase;
    }
}
