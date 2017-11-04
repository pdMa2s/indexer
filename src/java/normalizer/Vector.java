package src.java.normalizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Vector {
    protected Map<String, Double> vector;
    public Vector() {
        this.vector = new HashMap<>();
    }

    public void put(String term, double score){
        vector.put(term, score);
    }

    public Double getScore(String term){
        return vector.get(term);
    }

    public Set<String> getTerms(){
        return vector.keySet();
    }

    public boolean containsTerm(String term){
        return vector.containsKey(term);
    }
}
