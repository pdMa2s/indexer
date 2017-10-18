package src.java.query;


import src.java.index.Index;
import src.java.index.Posting;


import java.util.List;

public class DisjuntiveQueryProcessor implements QueryProcessor {

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

    @Override
    public void frequencyOfQueryWordsInDocument(Index index, List<Query> queries) {
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                List<Posting> postings = index.getPostingList(term);
                if (postings != null) {
                    for (Posting pst : postings) {
                        if (query.getScore(pst.getDocID()) == null)
                            query.addScore(pst.getDocID(), pst.getTermFreq());
                        else
                            query.addScore(pst.getDocID(), query.getScore(pst.getDocID()) +pst.getTermFreq());
                    }
                }
            }
        }
    }

}
