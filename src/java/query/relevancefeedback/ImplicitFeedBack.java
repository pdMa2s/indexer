package src.java.query.relevancefeedback;

import src.java.normalizer.Vector;
import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;
import src.java.query.QueryUpdater;

import java.util.List;
import java.util.Map;

/**
 * This implementation of {@link QueryUpdater} uses the top 10 results of the queries as positive
 * feedback to apply relevance feedback.
 */
public class ImplicitFeedBack implements QueryUpdater {
    private QueryIndex queryIndex;
    private DocumentIndex docIndex;

    private int topScore = 5;

    /**
     * Builds a ImplicitFeedBack object
     * @param queryIndex The {@link QueryIndex} that contains the query vectors
     * @param docIndex The {@link DocumentIndex} that contains the document vectors
     */
    public ImplicitFeedBack(QueryIndex queryIndex, DocumentIndex docIndex) {
        this.queryIndex = queryIndex;
        this.docIndex = docIndex;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQueries(List<Query> queries) {
        for (Query query : queries) {
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

    private double alpha(){
        return 1;
    }

    private double beta(){
        return 0.5;
    }
}
