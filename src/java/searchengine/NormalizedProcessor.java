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

public class NormalizedProcessor implements QueryProcessor{
    private QueryIndex queryIndex;

    public NormalizedProcessor(QueryIndex queryIndex) {
        this.queryIndex = queryIndex;
    }

    @Override
    public void processQueries(List<Query> queries, InvertedIndex idx, double threshold) {
        calculateNormalizedRanking(queries, idx, threshold);
    }
    public void calculateNormalizedRanking(List<Query> queries, InvertedIndex idx, double threshold){
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
