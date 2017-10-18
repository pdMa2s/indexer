package src.java.query;

import java.util.List;

public interface QueryResultWriter {
    void saveQueryResultsToFile(String fileName, List<Query> queries);
}
