package src.java.normalizer;

import java.util.HashMap;
import java.util.Map;

public class QueryVectors {
    private Map<Integer, Vector<String>> vectors;

    public QueryVectors() {
        this.vectors = new HashMap<>();
    }

    public void addTermScore(int queryId, String term){
        Vector<String> temp = vectors.get(queryId);
        if(temp != null){
            Double termFreq = temp.getScore(term);
            if(termFreq != null){
                temp.put(term, temp.getScore(term)+1);
            }
            else{
                temp.put(term,(double) 1);
            }
        }else{
            Vector<String> newVector = new Vector<>();
            newVector.put(term, (double) 1);
            vectors.put(queryId, newVector);
        }
    }
}
