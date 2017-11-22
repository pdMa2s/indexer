package src.java.query;

import java.util.*;

/**
 *
 * An object representation of a query. Stores a query id, a {@link List} of it's respective terms and {@link Map}
 * of scores to store results for each document.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 10-16-2017
 */
public class Query {

    private int id;
    private List<String> terms;
    private Map<Integer, Double> results;
    private double queryRecall;
    private double queryPrecision;
    private double queryPrecisionAtRank10;
    private double reciprocalRank;
    private double processingTime;
    private double fMeasure;

    /**
     * Constructs a {@link Query} object.
     * @param id An int identifier for the query.
     * @param terms A {@link List} of terms for this respective query.
     */
    public Query(int id, List<String> terms) {
        this.id = id;
        this.terms = terms;
        this.results = new HashMap<>();
    }

    /**
     * Clears the results.
     */
    public void clearResults() { results.clear(); }

    public void setProcessingTime(double processingTime){ this.processingTime = processingTime; }

    public double getProcessingTime(){ return processingTime; }

    public double getfMeasure(){ return fMeasure; }

    public void setfMeasure(double fMeasure){ this.fMeasure = fMeasure; }

    public void setQueryRecall(double accuracy){ this.queryRecall = accuracy; }

    public void setReciprocalRank(double reciprocalRank) { this.reciprocalRank = reciprocalRank; }

    public double getReciprocalRank() { return reciprocalRank; }

    public void setQueryPrecision(double precision){ this.queryPrecision = precision; }

    public double getQueryRecall() { return queryRecall; }

    public double getQueryPrecision() { return queryPrecision; }

    public double getQueryPrecisionAtRank10() {
        return queryPrecisionAtRank10;
    }

    public void setQueryPrecisionAtRank10(double queryPrecisionAtRank10) {
        this.queryPrecisionAtRank10 = queryPrecisionAtRank10;
    }

    /**
     *
     * @return The query id.
     */
    public int getId() {
        return id;
    }

    /**
     * This function stores the results or scores that a query obtain for a certain document id.
     * @param docId The id of the document.
     * @param score The score of a document for this query.
     */
    public void addScore(int docId, double score){
        results.put(docId, score);
    }
    public void setResults(Map<Integer, Double> results){
        this.results = results;
    }

    /**
     *
     * @param docId The id of the document of which you want to know the score.
     * @return The score associated with that document id.
     */
    public Double getScore(int docId){
        return results.get(docId);
    }

    /**
     *
     * @return A {@link Set} of document id's that where stored as results.
     */
    public Set<Integer> getDocIds(){
        return results.keySet();
    }

    /**
     *
     * @return A {@link List} of the terms of the query.
     */
    public List<String> getTerms() {
        return terms;
    }

    public Map<Integer, Double> getResults(){
        return results;
    }

    public Map<Integer, Double> getSortedResults(){
        ValueComparator bvc = new ValueComparator(results);
        TreeMap<Integer, Double> sortedMap = new TreeMap<>(bvc);
        sortedMap.putAll(results);
        return sortedMap;
    }
    class ValueComparator implements Comparator<Integer> {
        Map<Integer, Double> base;

        public ValueComparator(Map<Integer, Double> base) {
            this.base = base;
        }

        // Note: this comparator imposes orderings that are inconsistent with
        // equals.
        public int compare(Integer a, Integer b) {
            if (base.get(a) < base.get(b)) {
                return 1;
            } else {
                return -1;
            } // returning 0 would merge keys
        }
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public String toString() {
        return results.isEmpty() ? id + ": " + terms.toString() : id + ": " + results.toString();
    }
}
