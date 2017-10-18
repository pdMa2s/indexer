package src.java.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Query {

    private int id;
    private List<String> terms;
    private Map<Integer, Integer> results;

    public Query(int id, List<String> terms) {
        this.id = id;
        this.terms = terms;
        this.results = new HashMap<>();
    }

    public void clearResults() { results.clear(); }

    public int getId() {
        return id;
    }

    public void addScore(int docId, int score){
        results.put(docId, score);
    }

    public Integer getScore(int docId){
        return results.get(docId);
    }

    public Set<Integer> getDocIds(){
        return results.keySet();
    }
    public List<String> getTerms() {
        return terms;
    }

    @Override
    public String toString() {
        return results.isEmpty() ? id + ": " + terms.toString() : id + ": " + results.toString();
    }
}
