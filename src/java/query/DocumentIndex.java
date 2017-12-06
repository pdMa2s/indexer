package src.java.query;

import src.java.index.Index;
import src.java.normalizer.Vector;

import java.util.Map;
import java.util.Set;

public class DocumentIndex extends Index{

    public DocumentIndex(){}

    public void addTermsPerDocID(int docId, Map<String, Double> normalizedScores){
        Vector newVector = new Vector();
        for(String term : normalizedScores.keySet()){
            newVector.put(term, normalizedScores.get(term));
        }
        vectors.put(docId, newVector);
    }

    public Set<Integer> getDocIds(){ return vectors.keySet(); }

    public Vector getTermsVector(int id){ return getVector(id); }

    public void addVector(int docID, Vector newVector){ vectors.put(docID ,newVector);}
}
