package src.java.relevancefeedback;

import src.java.query.Query;

import java.util.List;

public class GoldStandardRelevance implements RelevanceQueryUpdater {

    private RelevanceIndex relevanceIndex;
    public GoldStandardRelevance(RelevanceIndex relevanceIndex ) {
        this.relevanceIndex = relevanceIndex;
    }

    @Override
    public void updateQueries(List<Query> queries) {

    }
}
