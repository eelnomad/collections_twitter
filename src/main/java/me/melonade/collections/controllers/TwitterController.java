package me.melonade.collections.controllers;

import me.melonade.collections.exceptions.CollectionException;
import me.melonade.collections.exceptions.TwitterStreamException;
import me.melonade.collections.mongo.MongoDB;
import me.melonade.collections.twitter.TwitterStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

/**
 * Created by damon on 7/18/2016.
 * The controllers containing all functions related to starting and stopping statusListeners and managing collections
 */

@RestController
@RequestMapping("/api")
public class TwitterController {

    private final TwitterStream twitterStream;

    @Autowired
    public TwitterController(TwitterStream twitterStream) {
        this.twitterStream = twitterStream;
    }

    @Autowired
    private MongoDB mongoDB;


    // Endpoint that starts statusListeners for collections, currently modified to only allow one statusListener at a time
    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public ResponseEntity<?> runCollection(@RequestParam(value = "collectionId") String collectionId) {
        // START: Addition to stop everything
        try {
            twitterStream.shutdown();
            mongoDB.setStreamOff();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        // END: Addition to stop everything
        try {
            twitterStream.addListener(collectionId);
            mongoDB.setStreamOn(collectionId);
            return ResponseEntity.ok().build();
        } catch (CollectionException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/new", method = RequestMethod.PUT)
    public ResponseEntity<?> newCollection(@RequestParam(value = "keywords", defaultValue = "Hillary,Clinton,Donald,Trump,Republican,Democrat") String[] keywords,
                                           @RequestParam(value = "desc", defaultValue = "2016 Election") String desc) {
        try {
            mongoDB.newStream(keywords, desc);
            return ResponseEntity.ok().build();
        } catch (CollectionException e) {
            System.out.println(e.toString());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @RequestMapping(value = "/stop", method = RequestMethod.GET)
    public ResponseEntity<?> stop(@RequestParam(value = "collectionId", required = false) String collectionId) {
        try {
            if (collectionId == null) {
                twitterStream.shutdown();
                mongoDB.setStreamOff();
            } else {
                twitterStream.shutdown(collectionId);
                mongoDB.setStreamOff(collectionId);
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public ResponseEntity<?> deleteCollection(@RequestParam(value = "collectionId") String collectionId) {
        try {
            twitterStream.shutdown(collectionId);
        } catch (TwitterStreamException e){
            System.out.println(e.toString());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        try {
            mongoDB.setStreamOff(collectionId);
            mongoDB.deleteStream(collectionId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }

    @RequestMapping(value = "/list/collections", method = RequestMethod.GET)
    public ResponseEntity<?> listCollections() {
        try {
            return ResponseEntity.ok(mongoDB.listCollectionOrganizer());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
