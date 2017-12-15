package src.java.query.queryExpansion;

import src.java.query.Query;
import src.java.query.QueryUpdater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * An implementation of the {@link QueryUpdater} that updates queries using query expansion.
 */
public class Word2VecQueryExpansionUpdater implements QueryUpdater {

    private QueryExpansionWord2Vec w2v;

    /**
     * Builds a Word2VecQueryExpansionUpdater object.
     * @param w2v Word2Vec object that return the nearest words.
     */
    public Word2VecQueryExpansionUpdater(QueryExpansionWord2Vec w2v){
        this.w2v = w2v;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateQueries(List<Query> queries) {
        for(Query query : queries){
            List<String> termToIterate = new ArrayList<>(query.getTerms());
            for(String term : termToIterate){
                Collection<String> lst = w2v.getSimilarWords(term);
                for(String termToBeAdded : lst){
                    query.addTerm(termToBeAdded);
                }
            }
        }
    }

}
