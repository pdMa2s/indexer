package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.index.Posting;
import src.java.query.Query;

import java.util.List;
import java.util.Set;

public class WordsInDocumentProcessor implements QueryProcessor {

    @Override
    public void processQueries(List<Query> queries, InvertedIndex index) {
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                Set<Posting> postings = index.getPostings(term);
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
}
