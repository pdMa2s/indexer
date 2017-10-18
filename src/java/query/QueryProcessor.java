package src.java.query;


import src.java.index.Index;

import java.util.List;

public interface QueryProcessor {
    void queryWordsInDocument(Index index, List<Query> queries);
    void frequencyOfQueryWordsInDocument(Index index, List<Query> queries);
}
