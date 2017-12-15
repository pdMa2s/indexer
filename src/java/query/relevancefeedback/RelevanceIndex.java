package src.java.query.relevancefeedback;


import java.util.HashMap;
import java.util.Map;

/**
 * This data structure represents the relevance scores for certain document for each query
 */
public class RelevanceIndex {
    private Map<Integer, HashMap<Integer, Integer>> relevanceIndex;

    /**
     * Constructs an empty index
     */
    public RelevanceIndex() {
        this.relevanceIndex = new HashMap<>();
    }

    /**
     *
     * @param queryId The id of the query
     * @param docId The id of the document
     * @return The relevance score of a document for certain query
     */
    public Integer getRelevanceScore(int queryId, int docId){
        return relevanceIndex.get(queryId).get(docId);
    }

    /**
     * Adds a relevance score to the index
     * @param queryId The query id to which the score belongs
     * @param docId The document id associated with the score
     * @param relevance The relevance score
     */
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
