package src.java.query;


import src.java.index.Index;
import src.java.index.Normalizer;

import java.util.List;

/**
 * Takes an {@link Index} and process it to obtain certain results that then stores on a {@link List} of
 * {@link Query} objects.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public interface QueryProcessor {
    /**
     * Counts the number of words in a query that appear in a document.
     * The results are stores in their respective {@link Query} object.
     * @param index An {@link Index} object where information is going to be extracted to answer the queries.
     * @param queries A {@link List} of {@link Query} objects with the content of which query. After the query
     *                as been processed it's results are going to be store in it's respective object.
     */
    void queryWordsInDocument(Index index, List<Query> queries);

    /**
     * Sums the frequency of document words that appear in a query.
     * The results are stores in their respective {@link Query} object.
     * @param index An {@link Index} object where information is going to be extracted to answer the queries.
     * @param queries A {@link List} of {@link Query} objects with the content of which query. After the query
     *                as been processed it's results are going to be store in it's respective object.
     */
    void frequencyOfQueryWordsInDocument(Index index, List<Query> queries);

    void tf_idf_QueryWordsInDocument(Index index, List<Query> queries, Normalizer nm);
}
