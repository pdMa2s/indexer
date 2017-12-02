package src.java.relevancefeedback;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelevanceIndex {
    private Map<Integer, Map<Integer, List<Integer>>> relevanceIndex;

    public RelevanceIndex() {
        this.relevanceIndex = new HashMap<>();
    }

    public void addRelevanceScore(int queryId, int relevance, int docId){
        if(relevanceIndex.containsKey(queryId)){
            Map<Integer, List<Integer>> queryScores = relevanceIndex.get(queryId);
            if(queryScores.containsKey(relevance)){
                queryScores.get(relevance).add(docId);
            }
            else{
                List<Integer> relevanceDocs = new ArrayList<>();
                relevanceDocs.add(docId);
                queryScores.put(relevance, relevanceDocs);
            }
        }
        else{
            Map<Integer, List<Integer>> queryScores = new HashMap<>();
            List<Integer> relevanceDocs = new ArrayList<>();
            relevanceDocs.add(docId);
            queryScores.put(relevance, relevanceDocs);
            relevanceIndex.put(queryId, queryScores);

        }
    }
}
