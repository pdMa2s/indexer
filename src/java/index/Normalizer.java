package src.java.index;

import java.util.HashMap;
import java.util.Map;

public class Normalizer {

    Map<Integer, Map<String, Double>> DirVector;
    Map<String, Integer> TermDocFreq;
    int CorpusSize;

    public Normalizer() {
        TermDocFreq = new HashMap<>();
        DirVector = new HashMap<>();
        this.CorpusSize = 0;
    }

    public void addTermDocFreq(String term, Integer freq) {
        TermDocFreq.put(term, freq);
    }

    public void addDirVectorOccurencce(int DocId, double TermScore, String term) {
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
}
