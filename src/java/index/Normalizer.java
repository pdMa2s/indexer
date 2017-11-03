package src.java.index;

import java.util.HashMap;
import java.util.Map;

public class Normalizer {

    Map<Integer, Map<String, Double>> DirVector;
    Map<Integer, Map<String, Double>> QueryVector;
    Map<Integer, Map<Integer, Double>> NormalizedResults;
    Map<String, Integer> TermDocFreq;
    int CorpusSize;

    public Normalizer() {
        TermDocFreq = new HashMap<>();
        DirVector = new HashMap<>();
        QueryVector = new HashMap<>();
        NormalizedResults = new HashMap<>();
        this.CorpusSize = 0;
    }

    public Map<Integer, Map<Integer, Double>> getNormalizedResults(){
        return NormalizedResults;
    }

    public void addTermDocFreq(String term, Integer freq) {
        TermDocFreq.put(term, freq);
    }

    public void addDirVectorOccurrencce(int DocId, double TermScore, String term) {
        Map<String, Double> temp = DirVector.get(DocId);
        if (temp != null) {
            temp.put(term, TermScore);
        } else {
            Map<String, Double> newEntry = new HashMap<>();
            newEntry.put(term, TermScore);
            DirVector.put(DocId, newEntry);
            CorpusSize++;
        }
    }

    public void addQueryVectorOccurrencce(int QueryId, String term){
        Map<String, Double> temp = QueryVector.get(QueryId);
        if(temp != null){
            Double termFreq = temp.get(term);
            if(termFreq != null){
                temp.put(term, temp.get(term)+1);
            }
            else{
                temp.put(term,(double) 1);
            }
        }else{
            Map<String, Double> newEntry = new HashMap<>();
            newEntry.put(term, (double) 1);
            QueryVector.put(QueryId, newEntry);
        }
    }

    public void normalize(){
        double normal;
        for(Integer doc : DirVector.keySet()){
            normal = 0;
            Map<String, Double> docTerms = DirVector.get(doc);
            for(String st : docTerms.keySet()){
                normal += Math.pow(docTerms.get(st), 2);
            }
            normal = Math.sqrt(normal);
            for(String st : docTerms.keySet()){
                docTerms.put(st, docTerms.get(st)/normal);
            }
        }
    }

    public void applyTFandIDFtoQueryMatrix(Index index){
        for(Integer QueryID : QueryVector.keySet()){
            Map<String, Double> temp = QueryVector.get(QueryID);
            for(String s : temp.keySet()){
                if(index.getPostingList(s)!= null)
                    temp.put(s,(1+Math.log10(temp.get(s)))*Math.log10(CorpusSize/index.getPostingList(s).size()));
            }
        }
    }

    public void normalizeQueryVector(){
        double normal;
        for(Integer QueryID : QueryVector.keySet()){
            normal = 0;
            Map<String, Double> Terms = QueryVector.get(QueryID);
            for(String st : Terms.keySet()){
                normal += Math.pow(Terms.get(st), 2);
            }
            normal = Math.sqrt(normal);
            for(String st : Terms.keySet()){
                Terms.put(st, Terms.get(st)/normal);
            }
        }
    }

    public void normalizeResults(){
        for(int queryID : QueryVector.keySet()){
            Map<String, Double> termsInQuery = QueryVector.get(queryID);
            for(String ter : termsInQuery.keySet()){
                for(int DocID : DirVector.keySet()){
                    Map<String, Double> DocTermList = DirVector.get(DocID);
                    if(DocTermList.containsKey(ter)){
                        Map<Integer, Double> results = NormalizedResults.get(queryID);
                        if(results != null) {
                            if(results.containsKey(DocID)){
                                results.put(DocID, results.get(DocID) + (DocTermList.get(ter)*termsInQuery.get(ter)));
                            }else{
                                results.put(DocID, DocTermList.get(ter)*termsInQuery.get(ter));
                            }
                        }
                        else{
                            Map<Integer, Double> res = new HashMap<>();
                            res.put(DocID, DocTermList.get(ter)*termsInQuery.get(ter));
                            NormalizedResults.put(queryID, res);
                        }
                    }
                }
            }
        }
    }
}
