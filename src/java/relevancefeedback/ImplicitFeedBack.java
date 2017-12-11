package src.java.relevancefeedback;

import src.java.normalizer.Vector;
import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.List;
import java.util.Map;

public class ImplicitFeedBack implements RelevanceQueryUpdater {
    private int topScore = 5;
    @Override
    public void updateQueries(QueryIndex queryIndex, DocumentIndex docIndex) {
        for (Query query : queryIndex.getQueries()) {
            Vector newQueryVector;
            int nrRelevantDocs = 0;
            Vector positiveScore = new Vector();
            Map<Integer, Double> queryTop10Results = query.getTop10SortedResults();

            for (int resultDocId : queryTop10Results.keySet()) {
                positiveScore = Vector.scalarProduct(beta(),
                        Vector.sumVectors(positiveScore, docIndex.getVector(resultDocId)));
                nrRelevantDocs++;

            }
            if(nrRelevantDocs != 0)
                positiveScore = Vector.scalarProduct((1/(double)nrRelevantDocs), positiveScore);

            newQueryVector = Vector.scalarProduct(alpha(),queryIndex.getVector(query.getId()));

            newQueryVector = Vector.sumVectors(newQueryVector,positiveScore.topScores(topScore) );
            queryIndex.putQueryVector(query.getId(), newQueryVector);

        }
    }

    @Override
    public void updateQueries(List<Query> queries) {

    }

    private double alpha(){
        return 1;
    }

    private double beta(){
        return 0.5;
    }
}
