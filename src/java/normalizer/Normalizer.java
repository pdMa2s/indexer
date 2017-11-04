package src.java.normalizer;

import src.java.index.DocumentIndex;
import src.java.index.Index;
import src.java.query.QueryIndex;

import java.util.HashMap;
import java.util.Map;

public class Normalizer {
    private DocumentIndex documentIndex;
    private QueryIndex queryIndex;
    Map<Integer, Map<Integer, Double>> NormalizedResults;

    public Normalizer(DocumentIndex documentIndex, QueryIndex queryIndex) {
        this.documentIndex = documentIndex;
        this.queryIndex = queryIndex;
        NormalizedResults = new HashMap<>();
    }

    public Map<Integer, Map<Integer, Double>> getNormalizedResults(){
        return NormalizedResults;
    }


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

    public void normalizeResults(){
        for(int queryID : queryIndex.getIds()){
            Vector termsInQuery = queryIndex.getVector(queryID);
            for(String ter : termsInQuery.keySet()){
                for(int DocID : documentIndex.getDocIds()){
                    Vector<String> docVector = documentIndex.getVector(DocID);
                    if(docVector.containsTerm(ter)){
                        Map<Integer, Double> results = NormalizedResults.get(queryID);
                        if(results != null) {
                            if(results.containsKey(DocID)){
                                results.put(DocID, results.get(DocID) + (docVector.getScore(ter)*termsInQuery.get(ter)));
                            }else{
                                results.put(DocID, docVector.getScore(ter)*termsInQuery.get(ter));
                            }
                        }
                        else{
                            Map<Integer, Double> res = new HashMap<>();
                            res.put(DocID, docVector.getScore(ter)*termsInQuery.get(ter));
                            NormalizedResults.put(queryID, res);
                        }
                    }
                }
            }
        }
    }
}
