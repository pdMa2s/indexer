package src.java.query;

import java.io.*;
import java.util.List;

/**
 * An implementation of {@link QueryResultWriter}, writes the query results on disk with the following format: <br>
 * query_id doc_id  doc_score
 *     1     1          4
 *     1     4          4
 *     ...
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public class ColumnResultWriter implements QueryResultWriter{
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveQueryResultsToFile(String fileName,List<Query> queries) {
        BufferedWriter writer;
        FileWriter fw;
        String delimiter = " ";
        try {
            fw = new FileWriter(fileName);
            writer = new BufferedWriter(fw);
            for(Query query: queries){
                for(Integer resultDocId : query.getDocIds()){
                    writer.write(query.getId() + delimiter + resultDocId + delimiter+ query.getScore(resultDocId) + "\n");
                }
                query.clearResults();
            }
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing index to file");
            System.exit(3);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
