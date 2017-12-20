package src.java.searchengine.queryprocessors;


import src.java.index.InvertedIndex;
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

    /**
     * Processes the queries calculates an appropriate result for them and stores results on their respective {@link Query}
     * objects. The results are only stored if they are equal or higher than the threshold value.
     * @param queries A {@link List} of {@link Query} objects that contains the term of the query and where the results
     *                will be stored.
     * @param idx An {@link InvertedIndex} that contains the index terms of the corpus
     * @param threshold The minimum values of the result scores
     */
    void processQueries( List<Query> queries, InvertedIndex idx, double threshold);
}

