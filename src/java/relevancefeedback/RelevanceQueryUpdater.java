package src.java.relevancefeedback;

import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.List;

public interface RelevanceQueryUpdater {
    void updateQueries(QueryIndex queryIndex, DocumentIndex docIndex);
    void updateQueries(List<Query> queries);
}
