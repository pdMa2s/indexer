package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.index.Posting;
import src.java.normalizer.Vector;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static src.java.constants.Constants.THRESHOLDDEFAULTVALUE;

/**
 * An implementation of {@link QueryProcessor} uses the dot product between vector to calculate th score for each
 * document
 */
public class NormalizedProcessor implements QueryProcessor{
    private QueryIndex queryIndex;

    /**
     * Constructs an {@link NormalizedProcessor} object
     * @param queryIndex A {@link src.java.query.QueryReader} to process the queries
     */
    public NormalizedProcessor(QueryIndex queryIndex) {
        this.queryIndex = queryIndex;
    }

    /**
     * Calculates the scores for each document using the dot product and the normalized scores of the document and
     * query
     * @param queries A {@link List} of {@link Query} objects that contains the term of the query and where the results
     *                will be stored.
     * @param idx An {@link InvertedIndex} that contains the index terms of the corpus
     * @param threshold The minimum values of the result scores
     */
    @Override
    public void processQueries(List<Query> queries, InvertedIndex idx, double threshold) {
        calculateNormalizedRanking(queries, idx, threshold);
    }
    private void calculateNormalizedRanking(List<Query> queries, InvertedIndex idx, double threshold){
        for(Query query : queries){
            long startTime = System.currentTimeMillis();
            Vector queryVector = queryIndex.getVector(query.getId());
            Map<Integer, Double> scores = query.getResults();

            for(String term : queryVector.getTerms()){
                Set<Posting> temp = idx.getPostings(term);
                if(temp != null){
                    for(Posting pst : temp){
                        Double queryVectorScore = queryVector.getScore(term);
                        calculateScore(scores, pst, queryVectorScore);
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
    private void calculateScore(Map<Integer, Double> scores, Posting pst, Double queryVectorScore){
        scores.merge(pst.getDocID(), pst.getWeight() * queryVectorScore, (a, b) -> a + (b));
    }

}
