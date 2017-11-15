package src.java.normalizer;

import src.java.index.Index;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Normalizer {

    public Map<String, Double> normalize(List<String> docTerms ){
        double norm = 0;
        Map<String, Double> scores = createOccurrenceMap(docTerms);

        for(String term : scores.keySet()){
            int nrOccurrence = scores.get(term).intValue();
            double tf = tf(nrOccurrence);
            norm += norm(tf);
            scores.put(term, tf);
        }
        norm = Math.sqrt(norm);

        for(String term : scores.keySet()) {
            scores.put(term, scores.get(term)/norm);
        }

        return scores;
    }
    private double tf(int occurrences){
        return 1+Math.log10(occurrences);
    }
    public void normalize(Index index){
        double normal;
        for(Integer doc : index.getIds()){
            normal = 0;
            Vector docTerms = index.getVector(doc);
            for(String term : docTerms.getTerms()){
                normal += norm(docTerms.getScore(term));
            }
            normal = Math.sqrt(normal);
            for(String term : docTerms.getTerms()){
                docTerms.put(term, docTerms.getScore(term)/normal);
            }
        }
    }

    private double norm(double score){
        return Math.pow(score, 2);
    }

    private Map<String, Double> createOccurrenceMap(List<String> docTerms){
        Map<String, Double> occurrences = new HashMap<>();
        for(String term : docTerms){
            Double termOccurrence = occurrences.get(term);
            if(termOccurrence == null)
                occurrences.put(term, (double) Collections.frequency(docTerms, term) );
        }
        return occurrences;
    }
}
