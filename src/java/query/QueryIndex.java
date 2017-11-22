package src.java.query;

import src.java.index.Index;
import src.java.index.InvertedIndex;
import src.java.normalizer.Vector;


import java.util.List;

/**
 * A extension of the class {@link Index} that is used to index queries and their respective vectors
 */
public class QueryIndex extends Index {
    private int corpusSize;
    private List<Query> queries;

    /**
     * Constructs an empty query index
     * @param corpusSize The size of the corpus use to create a the system's index
     */
    public QueryIndex(int corpusSize) {
        this.corpusSize = corpusSize;
    }

    /**
     * Fills the index with queries
     * @param queries A {@link List} of {@link Query} objects to fill the index
     */
    public void addQueries(List<Query> queries){
        this.queries = queries;
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                addQueryTermOccurrence(query.getId(), term);
            }
        }
    }

    private void addQueryTermOccurrence(int queryId, String term ){
        Vector tempVector = vectors.get(queryId);
        if(tempVector != null){
            Double termFreq = tempVector.getScore(term);
            if(termFreq != null){
                tempVector.put(term, tempVector.getScore(term)+1);
            }
            else{
                tempVector.put(term,(double) 1);
            }
        }else{
            Vector newVector = new Vector();
            newVector.put(term, (double) 1);
            vectors.put(queryId, newVector);
        }
    }

    /**
     * Applies the tf-idf weight to the query vectors
     * @param index The {@link InvertedIndex} of the corpus
     */
    public void applyTFAndIDFtoQueries(InvertedIndex index){
        for(Integer QueryID : getIds()){
            Vector temp = vectors.get(QueryID);
            for(String term : temp.getTerms()){
                if(index.getPostings(term)!= null){
                    double tfIdf = (1+Math.log10(temp.getScore(term)))*Math.log10((double)corpusSize/index.getPostings(term).size());
                    temp.put(term,tfIdf);
                }

            }
        }
    }

    /**
     *
     * @return A {@link List} of {@link Query} objects stored in the index
     */
    public List<Query> getQueries() {
        return queries;
    }

    @Override
    public String toString() {
        return "QueryIndex{" +
                "vectors=" + vectors +
                '}';
    }
}
