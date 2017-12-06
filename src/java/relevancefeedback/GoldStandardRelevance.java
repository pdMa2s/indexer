package src.java.relevancefeedback;

import src.java.normalizer.Vector;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.List;
import java.util.Map;

public class GoldStandardRelevance implements RelevanceQueryUpdater {

    private RelevanceIndex relevanceIndex;
    public GoldStandardRelevance(RelevanceIndex relevanceIndex ) {
        this.relevanceIndex = relevanceIndex;
    }

    @Override
    public void updateQueries(QueryIndex queryIndex) {
        for(Query query : queryIndex.getQueries()){
            Vector newQueryVector = new Vector();
            int nr_relevantDocs = 0;
            int nr_irrelevantDocs = 0;
            Vector relevantScore = new Vector();
            Vector irrelevantScore = new Vector();
            Map<Integer, Double> queryTop10Results = query.getTop10Results();
            for(int resultDocId : queryTop10Results.keySet()){
                if(this.relevanceIndex.getRelevanceScore(query.getId(), resultDocId) != null){
                    relevantScore = Vector.addVectors(queryIndex.getVector(query.getId(), ))
                }
            }
        }
    }

    private double getBeta(int relevanceScore){
        return 1/relevanceScore;
    }
}
