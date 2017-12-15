package src.java.query.relevancefeedback;

import src.java.normalizer.Vector;
import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;
import src.java.query.QueryUpdater;

import java.util.List;
import java.util.Map;

/**
 * This implementation of {@link QueryUpdater} uses the cranfield relevance scores, the gold standard,
 * to apply relevance feedback to the queries.
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 */
public class GoldStandardFeedBack implements QueryUpdater {

    private RelevanceIndex relevanceIndex;
    private QueryIndex queryIndex;
    private DocumentIndex docIndex;
    private int topScores = 5;

    /**
     * Constructs a GoldStandardFeedBack object.
     * @param relevanceIndex A {@link RelevanceIndex} object to consult the scores of each query
     * @param queryIndex A {@link QueryIndex} object with the query vectors
     * @param documentIndex A {@link DocumentIndex} object with the document vectors
     */
    public GoldStandardFeedBack(RelevanceIndex relevanceIndex, QueryIndex queryIndex, DocumentIndex documentIndex) {
        this.relevanceIndex = relevanceIndex;
        this.queryIndex = queryIndex;
        this.docIndex = documentIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQueries(List<Query> queries) {
        for(Query query : queries){
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
