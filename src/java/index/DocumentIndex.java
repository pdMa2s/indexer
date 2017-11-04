package src.java.index;

import src.java.normalizer.Vector;
public class DocumentIndex extends Index{

    public void addTermScore(int docId, String term, double termScore ){
        Vector tempVector = vectors.get(docId);
        if (tempVector != null) {
            tempVector.put(term, termScore);
        } else {
            Vector newVector = new Vector();
            newVector.put(term, termScore);
            vectors.put(docId, newVector);
        }
    }

}