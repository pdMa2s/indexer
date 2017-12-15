package src.java.query;

import java.util.List;

/**
 * Updates the queries to influence their results.
 */
public interface QueryUpdater {
    /**
     * Updates the terms of the queries.
     * @param queries A {@link List} of queries
     */
    void updateQueries(List<Query> queries);
}
