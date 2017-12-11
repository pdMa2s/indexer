package src.java.relevancefeedback;

import src.java.normalizer.Vector;
import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.List;
import java.util.Map;

public class GoldStandardFeedBack implements RelevanceQueryUpdater {

    private RelevanceIndex relevanceIndex;
    private int topScores = 5;
    public GoldStandardFeedBack(RelevanceIndex relevanceIndex ) {
        this.relevanceIndex = relevanceIndex;
    }

    @Override
    public void updateQueries(QueryIndex queryIndex, DocumentIndex docIndex) {
        for(Query query : queryIndex.getQueries()){
            Vector newQueryVector;
            int nrRelevantDocs = 0;
            int nrIrrelevantDocs = 0;
            Vector positiveScore = new Vector();
            Vector negativeScore = new Vector();
            Map<Integer, Double> queryTop10Results = query.getTop10SortedResults();

            for(int resultDocId : queryTop10Results.keySet()){
                Integer docRelevanceScore = relevanceIndex.getRelevanceScore(query.getId(), resultDocId);
                if(docRelevanceScore != null){
                    positiveScore = Vector.scalarProduct(beta(docRelevanceScore) ,
                            Vector.sumVectors(positiveScore, docIndex.getVector(resultDocId)));
                    nrRelevantDocs++;

                }
                else{
                    negativeScore = Vector.scalarProduct(sigma() ,
                            Vector.sumVectors(negativeScore, docIndex.getVector(resultDocId)));
                    nrIrrelevantDocs++;

                }
            }

            if(nrRelevantDocs != 0)
                positiveScore = Vector.scalarProduct((1/(double)nrRelevantDocs), positiveScore);
            if(nrIrrelevantDocs != 0)
                negativeScore = Vector.scalarProduct(((1/(double)nrIrrelevantDocs)), negativeScore);

            newQueryVector = Vector.scalarProduct(alpha(),queryIndex.getVector(query.getId()));
            newQueryVector = Vector.sumVectors(newQueryVector,
                    Vector.subtractVectors(positiveScore , negativeScore).topScores(topScores));
            queryIndex.putQueryVector(query.getId(), newQueryVector);
        }
    }

    @Override
    public void updateQueries(List<Query> queries) {

    }

    private double alpha(){
        return 1;
    }
    private double sigma(){
        return 0.2;
    }

    private double beta(int relevanceScore){
        return (double) 1/relevanceScore;
    }

}
