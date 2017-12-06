package src.java.relevancefeedback;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelevanceIndex {
    private Map<Integer, HashMap<Integer, Integer>> relevanceIndex;

    public RelevanceIndex() {
        this.relevanceIndex = new HashMap<>();
    }

    public Integer getRelevanceScore(int queryId, int docId){
        return relevanceIndex.get(queryId).get(docId);
    }
    public void addRelevanceScore(int queryId, int docId, int relevance){
        if(relevanceIndex.containsKey(queryId)){
            HashMap<Integer, Integer> queryScores = relevanceIndex.get(queryId);
            queryScores.put(docId, relevance);
        }
        else{
            HashMap<Integer, Integer> queryScores = new HashMap<>();
            queryScores.put(docId, relevance);
            relevanceIndex.put(queryId, queryScores);
        }
    }
}
