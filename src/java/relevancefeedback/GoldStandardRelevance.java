package src.java.relevancefeedback;

import src.java.normalizer.Vector;
import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.Map;

public class GoldStandardRelevance implements RelevanceQueryUpdater {

    private RelevanceIndex relevanceIndex;
    public GoldStandardRelevance(RelevanceIndex relevanceIndex ) {
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
            Map<Integer, Double> queryTop10Results = query.getTop10Results();

            for(int resultDocId : queryTop10Results.keySet()){
                Integer docRelevanceScore = relevanceIndex.getRelevanceScore(query.getId(), resultDocId);
                /*System.out.println(resultDocId);
                System.out.println(docRelevanceScore);
                System.exit(69);*/
                if(docRelevanceScore != null){
                    positiveScore = Vector.scalarProduct(beta(docRelevanceScore) ,
                            Vector.sumVectors(positiveScore, docIndex.getVector(resultDocId)));
                    nrRelevantDocs++;
                    //System.out.println(positiveScore);
                }
                else{
                    negativeScore = Vector.scalarProduct(sigma() ,
                            Vector.sumVectors(negativeScore, docIndex.getVector(resultDocId)));
                    nrIrrelevantDocs++;
                    //System.out.println(negativeScore);
                }
            }

            if(nrRelevantDocs != 0)
                positiveScore = Vector.scalarProduct((1/(double)nrRelevantDocs), positiveScore);
            if(nrIrrelevantDocs != 0)
                negativeScore = Vector.scalarProduct(((1/(double)nrIrrelevantDocs)), negativeScore);

            newQueryVector = Vector.scalarProduct(alpha(),queryIndex.getVector(query.getId()));
            //System.out.println("query " + newQueryVector);
            System.out.println("positive " + positiveScore);
            System.out.println("negative " + negativeScore);
            System.out.println("sub " + Vector.subtractVectors(positiveScore , negativeScore));
            newQueryVector = Vector.sumVectors(newQueryVector , Vector.subtractVectors(positiveScore , negativeScore));
            queryIndex.putQueryVector(query.getId(), newQueryVector);

            //System.out.println(queryIndex.getVector(query.getId()));
            //System.exit(69);
        }
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
