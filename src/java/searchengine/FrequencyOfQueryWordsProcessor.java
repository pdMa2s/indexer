package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.index.Posting;
import src.java.query.Query;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static src.java.constants.Constants.THRESHOLDDEFAULTVALUE;

public class FrequencyOfQueryWordsProcessor implements QueryProcessor {
    @Override
    public void processQueries(List<Query> queries, InvertedIndex index, double threshold) {
        for(Query query: queries) {
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
