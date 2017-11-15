package src.java.searchengine;

import src.java.index.InvertedIndex;
import src.java.index.Posting;
import src.java.normalizer.Vector;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.List;
import java.util.Set;

public class NormalizedProcessor implements QueryProcessor{
    private QueryIndex queryIndex;

    public NormalizedProcessor(QueryIndex queryIndex) {
        this.queryIndex = queryIndex;
    }

    @Override
    public void processQueries(List<Query> queries, InvertedIndex idx) {

        calculateNormalizedRanking(queries, idx);
    }
    public void calculateNormalizedRanking(List<Query> queries, InvertedIndex idx){
        for(Query query : queries){
            Vector queryVector = queryIndex.getVector(query.getId());
            for(String term : queryVector.getTerms()){
                Set<Posting> temp = idx.getPostings(term);
                if(temp != null){
                    for(Posting pst : temp){
                        Double result = query.getScore(pst.getDocID());
                        if(result != null) {
                            query.addScore(pst.getDocID(), result + (pst.getNormalizedWeight() * queryVector.getScore(term)));
                        }
                        else {
                            query.addScore(pst.getDocID(), pst.getNormalizedWeight() * queryVector.getScore(term));
                        }
                    }
                }
            }
        }
    }

}
