package src.java.query;


import src.java.index.Index;
import src.java.index.Normalizer;
import src.java.index.Posting;


import java.util.List;

/**
 *
 * An implementation of {@link QueryProcessor} assumes disjunctive (“OR”) queries: all words in a query are
 * combined using the OR operator.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public class DisjuntiveQueryProcessor implements QueryProcessor {

    /**
     *{@inheritDoc}
     */
    @Override
    public void queryWordsInDocument(Index index, List<Query> queries) {
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                List<Posting> postings = index.getPostingList(term);
                if (postings != null) {
                    for (Posting pst : postings) {
                        if (query.getScore(pst.getDocID()) == null)
                            query.addScore(pst.getDocID(), 1);
                        else
                            query.addScore(pst.getDocID(), query.getScore(pst.getDocID()) + 1);
                    }
                }
            }
        }
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void frequencyOfQueryWordsInDocument(Index index, List<Query> queries) {
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                List<Posting> postings = index.getPostingList(term);
                if (postings != null) {
                    for (Posting pst : postings) {
                        Integer score = query.getScore(pst.getDocID());
                        if (score == null)
                            query.addScore(pst.getDocID(), pst.getTermFreq());
                        else
                            query.addScore(pst.getDocID(), score +pst.getTermFreq());
                    }
                }
            }
        }
    }

    public void tf_idf_QueryWordsInDocument(Index index, List<Query> queries, Normalizer nm){
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                nm.addQueryVectorOccurencce(query.getId(), term);
            }
        }
        nm.applyTFandIDFtoQueryMatrix(index);
        nm.normalizeQueryVector();
    }
}
