package src.java.normalizer;

import src.java.index.Index;

public class Normalizer {

    public void normalize(Index index){
        double normal;
        for(Integer doc : index.getIds()){
            normal = 0;
            Vector docTerms = index.getVector(doc);
            for(String term : docTerms.getTerms()){
                normal += Math.pow(docTerms.getScore(term), 2);
            }
            normal = Math.sqrt(normal);
            for(String term : docTerms.getTerms()){
                docTerms.put(term, docTerms.getScore(term)/normal);
            }
        }
    }


}
