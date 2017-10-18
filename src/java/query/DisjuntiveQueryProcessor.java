package src.java.query;


import src.java.index.Index;
import src.java.index.Posting;


import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class DisjuntiveQueryProcessor implements QueryProcessor {

    private Index index;
    public DisjuntiveQueryProcessor(Index index) {
        this.index = index;
    }


    @Override
    public void queryWordsInDocument(List<Query> queries) {
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
    public void frequencyOfQueryWordsInDocument(List<Query> queries) {
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

    @Override
    public void saveQueryResultsToFile(String fileName,List<Query> queries) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing index to file");
            System.exit(3);
        }
        writer.printf("%8s%8s%10s\n", "query_id","doc_id","doc_score");
        for(Query query: queries){
            for(Integer resultDocId : query.getDocIds()){
                //System.out.println(query.getId()+"\t"+resultDocId+"\t"+query.getScore(resultDocId));
                writer.printf("%8d%8d%10d\n",query.getId(),resultDocId,query.getScore(resultDocId));
            }
        }
        writer.close();
    }
}
