package src.java.searchengine;


import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;
import src.java.query.Query;

import java.util.List;

/**
 * Takes an {@link InvertedIndex} and process it to obtain certain results that then stores on a {@link List} of
 * {@link Query} objects.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public interface QueryProcessor {

    void processQueries( List<Query> queries, InvertedIndex idx, double threshold);
}

