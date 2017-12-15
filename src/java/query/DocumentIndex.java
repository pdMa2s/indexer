package src.java.query;

import src.java.index.Index;
import src.java.normalizer.Vector;

import java.util.Map;
import java.util.Set;

/**
 * This data structure stores vector of term weight for each document
 * @author Pedro Matos
 * @author David Ferreira
 * @since 12-15-2017
 */
public class DocumentIndex extends Index{

    /**
     * Adds terms to each document id
     * @param docId The id of the document
     * @param normalizedScores A {@link Map} where the keys are the terms and the values their respective score
     */
    public void addTermsPerDocID(int docId, Map<String, Double> normalizedScores){
        Vector newVector = new Vector();
        for(String term : normalizedScores.keySet()){
            newVector.put(term, normalizedScores.get(term));
        }
        vectors.put(docId, newVector);
    }

    /**
     *
     * @return A {@link Set} with the ids of the documents
     */
    public Set<Integer> getDocIds(){ return vectors.keySet(); }


}
