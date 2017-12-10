package src.java.normalizer;

import java.util.*;

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

    public int size(){
        return vector.size();
    }

    public static Vector sumVectors(Vector a, Vector b){
        Vector result = new Vector();
        Vector tempBig;
        Vector tempSmall;
        if(a.size() > b.size()){
            tempBig = a;
            tempSmall = b;
        }
        else{
            tempBig = b;
            tempSmall = a;
        }
        for(String term : tempBig.getTerms()){
            Double smallVectorScore = tempSmall.getScore(term);
            Double bigVectorScore = tempBig.getScore(term);
            if(smallVectorScore != null){
                double dimSum = smallVectorScore + bigVectorScore;
                result.put(term, dimSum);
            }
            else{
                result.put(term, bigVectorScore);
            }

        }
        return result;

    }

    public static Vector subtractVectors(Vector a, Vector b){

        return Vector.sumVectors(a, Vector.scalarProduct(-1, b));

    }

    public static Vector scalarProduct(double scalar, Vector a){
        Vector result = new Vector();
        for(Map.Entry<String, Double> entry : a.vector.entrySet()){
            result.put(entry.getKey(), entry.getValue() * scalar);
        }
        return result;
    }

    public Vector topScores(int top){
        List<Map.Entry<String, Double>> l = new ArrayList<>(vector.entrySet());
        l.sort((o1, o2) -> {
            double diff = o1.getValue() - o2.getValue();
            if(diff < 0)
                return 1;
            if(diff > 0)
                return -1;
            else
                return 0;
        });
        List<Map.Entry<String, Double>> temp = new ArrayList<>();
        for (Map.Entry<String, Double> aL : l) {
            if (temp.size() > 5)
                break;
            temp.add(aL);
        }
        l = temp;

        Vector result = new Vector();
        for(Map.Entry<String, Double> entry : l){
            result.vector.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
    @Override
    public String toString() {
        return
                "vector={" + vector + '}';
    }
}
