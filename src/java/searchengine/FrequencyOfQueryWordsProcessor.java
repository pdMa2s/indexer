package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.index.Posting;
import src.java.query.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static src.java.constants.Constants.THRESHOLDDEFAULTVALUE;

/**
 * An implementation of {@link QueryProcessor} that sums the frequency of the query words that appear in a document
 */
public class FrequencyOfQueryWordsProcessor implements QueryProcessor {
    /**
     * Sums the frequency of document words that appear in a query.
     * The results are stores in their respective {@link Query} object.
     * @param index An {@link InvertedIndex} object where information is going to be extracted to answer the queries.
     * @param queries A {@link List} of {@link Query} objects with the content of which query. After the query
     *                as been processed it's results are going to be store in it's respective object.
     * @param threshold The minimum value of the results
     */
    @Override
    public void processQueries(List<Query> queries, InvertedIndex index, double threshold) {
        for(Query query: queries) {
            long startTime = System.currentTimeMillis();
            Map<Integer, Double> scores = query.getResults();
            for (String term : query.getTerms()) {
                Set<Posting> postings = index.getPostings(term);
                if (postings != null) {
                    for (Posting pst : postings) {
                        calculateScore(scores, pst);
                    }

                }
            }
            filterResults(threshold, query);
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            query.setProcessingTime(elapsedTime);
        }
    }
    private void filterResults(double threshold, Query query){
        Map<Integer, Double> scores = query.getResults();
        if(threshold != THRESHOLDDEFAULTVALUE){
            query.setResults( scores.entrySet()
                    .stream()
                    .filter(sc -> sc.getValue() >= threshold)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
        }

    }
    private void calculateScore(Map<Integer, Double> scores, Posting pst){
        scores.merge(pst.getDocID(), pst.getWeight(), (a, b) -> a + b);
    }
}
