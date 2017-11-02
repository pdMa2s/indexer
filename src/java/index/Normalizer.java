package src.java.index;

import java.util.HashMap;
import java.util.Map;

public class Normalizer {

    Map<Integer, Map<String, Double>> DirVector;
    Map<String, Integer> TermDocFreq;

    public Normalizer() {
        TermDocFreq = new HashMap<>();
        DirVector = new HashMap<>();
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
        }
    }

    public void normalize(){

    }
}
