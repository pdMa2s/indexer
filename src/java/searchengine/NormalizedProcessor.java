package src.java.searchengine;

import src.java.index.DocumentIndex;
import src.java.normalizer.Vector;
import src.java.query.Query;
import src.java.query.QueryIndex;

import java.util.List;

public class NormalizedProcessor implements QueryProcessor{
    private DocumentIndex documentIndex;
    private QueryIndex queryIndex;

    public NormalizedProcessor(DocumentIndex documentIndex, QueryIndex queryIndex) {
        this.documentIndex = documentIndex;
        this.queryIndex = queryIndex;
    }

    @Override
    public void processQueries( List<Query> queries) {
        calculateNormalizedRanking(queries);
    }
    public void calculateNormalizedRanking(List<Query> queries){
        for(Query query : queries){
            Vector queryVector = queryIndex.getVector(query.getId());
            for(String ter : queryVector.getTerms()){
                for(int docID : documentIndex.getIds()){
                    Vector docVector = documentIndex.getVector(docID);
                    if(docVector.containsTerm(ter)){
                        Double results = query.getScore(docID);
                        if(results != null)
                            query.addScore(docID, results + (docVector.getScore(ter)*queryVector.getScore(ter)));
                        else
                            query.addScore(docID, docVector.getScore(ter)*queryVector.getScore(ter));

                    }
                }
            }
        }
    }

}
