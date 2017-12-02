package src.java.relevancefeedback;

import src.java.query.Query;

import java.util.List;

public interface RevelanceQueryUpdater {
    void updateQueries(List<Query> queries);
}
