package src.java.query;

import java.util.List;

public interface RelevanceQueryUpdater {
    void updateQueries(QueryIndex queryIndex, DocumentIndex docIndex);
    void updateQueries(List<Query> queries);
}
