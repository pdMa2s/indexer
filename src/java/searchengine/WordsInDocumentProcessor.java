package src.java.searchengine;


import src.java.index.InvertedIndex;
import src.java.index.Posting;
import src.java.query.Query;


import java.util.List;

/**
 *
 * An implementation of {@link QueryProcessor} assumes disjunctive (“OR”) queries: all words in a query are
 * combined using the OR operator.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public class WordsInDocumentProcessor implements QueryProcessor {

    private InvertedIndex index;

    public WordsInDocumentProcessor(InvertedIndex index) {
        this.index = index;
    }

    /**
     * Counts the number of words in a query that appear in a document.
     * The results are stores in their respective {@link Query} object.
     * @param queries A {@link List} of {@link Query} objects with the content of which query. After the query
     *                as been processed it's results are going to be store in it's respective object.
     */
    public void queryWordsInDocument( List<Query> queries) {
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
    public void processQueries( List<Query> queries) {
        queryWordsInDocument(queries);
    }
}
