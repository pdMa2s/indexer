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
    private double NDCG;


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

    /**
     *
     * @param processingTime The time that the query took to be processed
     */
    public void setProcessingTime(double processingTime){ this.processingTime = processingTime; }

    /**
     *
     * @return The processing time of the query
     */
    public double getProcessingTime(){ return processingTime; }

    /**
     *
     * @return The query f measure
     */
    public double getFMeasure(){ return fMeasure; }

    /**
     *
     * @param fMeasure The query's f measure
     */
    public void setFMeasure(double fMeasure){ this.fMeasure = fMeasure; }

    /**
     *
     * @param accuracy The recall of the query
     */
    public void setQueryRecall(double accuracy){ this.queryRecall = accuracy; }

    /**
     *
     * @param reciprocalRank The reciprocal rank of the query
     */
    public void setReciprocalRank(double reciprocalRank) { this.reciprocalRank = reciprocalRank; }

    /**
     *
     * @return The queries reciprocal rank
     */
    public double getReciprocalRank() { return reciprocalRank; }

    /**
     *
     * @param precision The precision of the query
     */
    public void setQueryPrecision(double precision){ this.queryPrecision = precision; }

    /**
     *
     * @return The recall of the query
     */
    public double getQueryRecall() { return queryRecall; }

    /**
     *
     * @return The precision of the query
     */
    public double getQueryPrecision() { return queryPrecision; }

    /**
     *
     * @return The precision at rank 10
     */
    public double getQueryPrecisionAtRank10() {
        return queryPrecisionAtRank10;
    }

    /**
     *
     * @param queryPrecisionAtRank10 The precision of the query at rank 10
     */
    public void setQueryPrecisionAtRank10(double queryPrecisionAtRank10) {
        this.queryPrecisionAtRank10 = queryPrecisionAtRank10;
    }

    /**
     *
     * @return The NDCG of the query
     */
    public double getNDCG() {
        return NDCG;
    }

    /**
     *
     * @param NDCG The NDCG of the query
     */
    public void setNDCG(double NDCG) {
        this.NDCG = NDCG;
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

    /**
     *
     * @param results A {@link Map} object that contains the results for each term
     */
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

    /**
     *
     * @return A {@link Map} where the keys are the ids of the documents and the values are the scores of each
     * respective document.
     */
    public Map<Integer, Double> getResults(){
        return results;
    }

    /**
     *
     * @return Similar to getSortedResults but only results the 10 highest results
     */
    public Map<Integer, Double> getTop10SortedResults(){
        Map<Integer, Double> top10 = new TreeMap<>();
        Map<Integer, Double> sorted = getSortedResults();
        for(Map.Entry<Integer, Double> entry : sorted.entrySet()){
            top10.put(entry.getKey(), entry.getValue());
            if(top10.size() == 10)
                break;
        }
        return top10;

    }

    /**
     *
     * @return Similar to getResults but the results are sorted from the highest to lowest score
     */
    public Map<Integer, Double> getSortedResults(){
        ValueComparator bvc = new ValueComparator(results);
        TreeMap<Integer, Double> sortedMap = new TreeMap<>(bvc);
        sortedMap.putAll(results);
        return sortedMap;
    }

    /**
     * Adds a term to the query
     * @param term The term to add
     */
    public void addTerm(String term){
        terms.add(term);
    }

    class ValueComparator implements Comparator<Integer> {
        Map<Integer, Double> base;

        ValueComparator(Map<Integer, Double> base) {
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
