package me.melonade.collections.mongo;

import com.mongodb.client.FindIterable;
import me.melonade.collections.conf.StopWords;
import me.melonade.collections.exceptions.CollectionException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.CharArraySet;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.shingle.ShingleFilter;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Damon on 12/30/2016.
 * Calcuate stats related to collections
 */

@Service
public class Stats {

    @Autowired
    private MongoDB mongoDB;

    // 100% sure im doing something stupid here
    public Map<String, Integer> wordCount(String collectionId) throws IOException, CollectionException {
        // Date to word count from
        DateTime date = new DateTime(DateTimeZone.UTC).minusHours(1);

        Map<String, Integer> wordCount = new HashMap<>();

        // Create string to parse? gotta be a better way than this O.O
        StringBuilder toParse = new StringBuilder();
        FindIterable<Document> organizer = mongoDB.getCollection(collectionId).find(new Document("createDate", new Document("$gt", date.toDate())));
        for (Document document : organizer) {
            toParse.append(". ").append(document.get("text"));
        }


        CharArraySet stopSet = new CharArraySet(0,true);
        stopSet.addAll(StopAnalyzer.ENGLISH_STOP_WORDS_SET);
        stopSet.addAll(StopWords.stopWords);
        stopSet.addAll(mongoDB.findKeywordsByCollectionId(collectionId));

        Reader reader = new StringReader(toParse.toString());
        Analyzer analyzer = new StandardAnalyzer(stopSet);
        TokenStream ts = new ShingleFilter(analyzer.tokenStream("text", reader), 3);
        CharTermAttribute charTermAttribute = ts.addAttribute(CharTermAttribute.class);

        ts.reset();
        while (ts.incrementToken()) {
            if (!charTermAttribute.toString().contains("_")) {
                if (wordCount.containsKey(charTermAttribute.toString()))
                    wordCount.replace(charTermAttribute.toString(), wordCount.get(charTermAttribute.toString()) + 1);
                else
                    wordCount.put(charTermAttribute.toString(), 1);
            }
        }
        ts.end();
        ts.close();
        wordCount = sortByValue(wordCount);

        Map<String,Integer> finalWordCount = new HashMap<>();
        int i = 0;
        Iterator wordsIterator = wordCount.entrySet().iterator();
        while (wordsIterator.hasNext() && i < 100) {
            Map.Entry<String,Integer> val = (Map.Entry)wordsIterator.next();
            finalWordCount.put(val.getKey(), val.getValue());
            i++;
        }
        return finalWordCount;
    }

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public List<List<Object>> wordCountToList(Map<String, Integer> wordCount) {
        List<List<Object>> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            List<Object> val = new ArrayList<>();
            val.add(entry.getKey());
            val.add(entry.getValue());
            result.add(val);
        }
        return result;
    }
}
