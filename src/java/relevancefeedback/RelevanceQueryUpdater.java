package src.java.relevancefeedback;

import src.java.query.DocumentIndex;
import src.java.query.QueryIndex;

public interface RelevanceQueryUpdater {
    void updateQueries(QueryIndex queryIndex, DocumentIndex docIndex);
}
