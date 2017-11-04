package src.java.index;

import src.java.normalizer.Vector;

import java.util.Map;
import java.util.Set;

public class DocumentIndex {
    private Map<Integer, Vector<String>> vectors;

    public void addTermScore(int docId, String term, double termScore ){
        Vector<String> tempVector = vectors.get(docId);
        if (tempVector != null) {
            tempVector.put(term, termScore);
        } else {
            Vector<String> newVector = new Vector<>();
            newVector.put(term, termScore);
            vectors.put(docId, newVector);
        }
    }

    public Set<Integer> getDocIds() {
        return vectors.keySet();
    }

    public Vector<String> getVector(int docInd){
        return vectors.get(docInd);
    }
}