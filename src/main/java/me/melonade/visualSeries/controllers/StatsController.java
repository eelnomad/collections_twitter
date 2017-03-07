package me.melonade.visualSeries.controllers;

import me.melonade.visualSeries.exceptions.CollectionException;
import me.melonade.visualSeries.mongo.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.TwitterException;

/**
 * Created by damon on 8/3/2016.
 * Controller for Stats
 */

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired
    Stats stats;

    // Endpoint that starts statusListeners for collections, currently modified to only allow one statusListener at a time
    @RequestMapping(value = "/wordcount", method = RequestMethod.GET)
    public ResponseEntity<?> runCollection(@RequestParam(value = "collectionId") String collectionId) {
        try {
            return ResponseEntity.ok(stats.wordCountToList(stats.wordCount(collectionId)));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
