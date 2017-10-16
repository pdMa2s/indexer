package src.java.query;


import java.util.List;

public interface QueryProcessor {
    void queryWordsInDocument(List<Query> queries);
    void frequencyOfQueryWordsInDocument(List<Query> queries);
    void saveQueryResultsToFile(String fileName, List<Query> queries);
}
