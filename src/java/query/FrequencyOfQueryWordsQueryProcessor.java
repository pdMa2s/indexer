package src.java.query;

import src.java.index.InvertedIndex;
import src.java.index.Posting;

import java.util.List;

public class FrequencyOfQueryWordsQueryProcessor implements QueryProcessor {
    private InvertedIndex index;

    public FrequencyOfQueryWordsQueryProcessor(InvertedIndex index) {
        this.index = index;
    }

    @Override
    public void processQueries( List<Query> queries) {
        frequencyOfQueryWordsInDocument( queries);
    }
    /**
     * Sums the frequency of document words that appear in a query.
     * The results are stores in their respective {@link Query} object.
     * @param queries A {@link List} of {@link Query} objects with the content of which query. After the query
     *                as been processed it's results are going to be store in it's respective object.
     */
    public void frequencyOfQueryWordsInDocument( List<Query> queries) {
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                List<Posting> postings = index.getPostingList(term);
                if (postings != null) {
                    for (Posting pst : postings) {
                        Double score = query.getScore(pst.getDocID());
                        if (score == null)
                            query.addScore(pst.getDocID(), pst.getTermOccurrences());
                        else
                            query.addScore(pst.getDocID(), score +pst.getTermOccurrences());
                    }
                }
            }
        }
    }
}
