package src.java.query;

import src.java.index.Index;
import src.java.index.InvertedIndex;
import src.java.normalizer.Vector;


import java.util.List;

public class QueryIndex extends Index {
    private int corpusSize;

    public QueryIndex(int corpusSize) {
        this.corpusSize = corpusSize;
    }

    public void addQueries(List<Query> queries){
        for(Query query: queries) {
            for (String term : query.getTerms()) {
                addQueryTermOccurrence(query.getId(), term);
            }
        }
    }

    private void addQueryTermOccurrence(int queryId, String term ){
        Vector tempVector = vectors.get(queryId);
        if(tempVector != null){
            Double termFreq = tempVector.getScore(term);
            if(termFreq != null){
                tempVector.put(term, tempVector.getScore(term)+1);
            }
            else{
                tempVector.put(term,(double) 1);
            }
        }else{
            Vector newVector = new Vector();
            newVector.put(term, (double) 1);
            vectors.put(queryId, newVector);
        }
    }

    public void applyTFAndIDFtoQueries(InvertedIndex index){
        for(Integer QueryID : getIds()){
            Vector temp = vectors.get(QueryID);
            for(String term : temp.getTerms()){
                if(index.getPostingList(term)!= null)
                    temp.put(term,(1+Math.log10(temp.getScore(term)))*Math.log10(corpusSize/index.getPostingList(term).size()));
            }
        }
    }

}
