package src.java.query;

import src.java.normalizer.Normalizer;

import java.util.List;

/**
 *
 * A writer for the results of the queries after they have been processed.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public interface QueryResultWriter {
    /**
     * Writes the results of a {@link List} of {@link Query} objects on disk.
     * @param fileName The name of a file where the results of the queries will be written into.
     * @param queries A {@link List} of {@link Query} that contain the results for each query.
     */
    void saveQueryResultsToFile(String fileName, List<Query> queries);
    void saveNormalizedResultsToFile(String fileName, List<Query> queries, Normalizer nm);
}
