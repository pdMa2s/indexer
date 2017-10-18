package src.java.query;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class ColumnResultWriter implements QueryResultWriter{
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
            query.clearResults();
        }
        writer.close();
    }
}
