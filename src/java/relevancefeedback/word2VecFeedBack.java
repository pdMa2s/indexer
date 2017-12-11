package src.java.relevancefeedback;

import src.java.query.DocumentIndex;
import src.java.query.Query;
import src.java.query.QueryIndex;
import src.java.word2vec.QueryExpansionWord2Vec;

import java.util.Collection;
import java.util.List;

public class word2VecFeedBack implements RelevanceQueryUpdater{

    private QueryExpansionWord2Vec w2v;

    public word2VecFeedBack(QueryExpansionWord2Vec w2v){
        this.w2v = w2v;
    }
    @Override
    public void updateQueries(QueryIndex queryIndex, DocumentIndex docIndex) {
        List<Query> queries = queryIndex.getQueries();
        for(Query query : queries){
            List<String> terms = query.getTerms();
            for(String term : terms){
                Collection<String> lst = w2v.getSimilarWords(term);
                for(String termToBeAdded : lst){
                    query.addTerm(termToBeAdded);
                }
            }
        }
    }
}
