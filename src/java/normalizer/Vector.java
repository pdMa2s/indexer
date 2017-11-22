package src.java.normalizer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A vector of scores for each term
 */
public class Vector {
    protected Map<String, Double> vector;

    /**
     * Constructs an empty {@link Vector}
     */
    public Vector() {
        this.vector = new HashMap<>();
    }

    /**
     *
     * @param term A key term
     * @param score The score associated with the value
     */
    public void put(String term, double score){
        vector.put(term, score);
    }

    /**
     *
     * @param term The key term
     * @return The score of the term or null if the term is not in the vector
     */
    public Double getScore(String term){
        return vector.get(term);
    }

    /**
     *
     * @return A {@link Set} of terms
     */
    public Set<String> getTerms(){
        return vector.keySet();
    }


    @Override
    public String toString() {
        return "Vector{" +
                "vector=" + vector +
                '}';
    }
}
