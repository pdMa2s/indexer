package src.java.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private Map<Integer, Integer> results;

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
    public void addScore(int docId, int score){
        results.put(docId, score);
    }

    /**
     *
     * @param docId The id of the document of which you want to know the score.
     * @return The score associated with that document id.
     */
    public Integer getScore(int docId){
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
     *{@inheritDoc}
     */
    @Override
    public String toString() {
        return results.isEmpty() ? id + ": " + terms.toString() : id + ": " + results.toString();
    }
}
