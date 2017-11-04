package src.java.query;


import src.java.index.InvertedIndex;
import src.java.normalizer.Normalizer;

import java.util.List;

/**
 * Takes an {@link InvertedIndex} and process it to obtain certain results that then stores on a {@link List} of
 * {@link Query} objects.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public interface QueryProcessor {

    void processQueries(InvertedIndex index, List<Query> queries);
}

