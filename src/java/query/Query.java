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


    public Set<Map.Entry<Integer, Double>> getSortedResults(){
        SortedSet<Map.Entry<Integer, Double>> sortedSet = new TreeSet<>((o1, o2) -> {
            double difference = o2.getValue() - o1.getValue();
            if(difference > 0)
                return 1;
            else if(difference < 0)
                    return -1;
            return 0;
        });
        sortedSet.addAll(results.entrySet());
        return sortedSet;
    }
    /**
     *{@inheritDoc}
     */
    @Override
    public String toString() {
        return results.isEmpty() ? id + ": " + terms.toString() : id + ": " + results.toString();
    }
}
