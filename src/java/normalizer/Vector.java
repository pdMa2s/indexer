package src.java.normalizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Vector<T> {
    protected Map<T, Double> vector;
    public Vector() {
        this.vector = new HashMap<>();
    }

    public void put(T key, double score){
        vector.put(key, score);
    }

    public Double getScore(T key){
        return vector.get(key);
    }

    public Set<T> keySet(){
        return vector.keySet();
    }

    public boolean contanisTerm(String term){
        return vector.containsKey(term);
    }
}
