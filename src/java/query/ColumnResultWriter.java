package src.java.query;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

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
        String delimiter = "\t";
        try {
            fw = new FileWriter(fileName);
            writer = new BufferedWriter(fw);
            writer.write("query_id" + delimiter+ "doc_id"+delimiter+"doc_score" +"\n");
            NumberFormat formatter = new DecimalFormat("#0.00000");
            for(Query query: queries){
                for(Map.Entry<Integer, Double> entry: query.getSortedResults().entrySet()){

                    writer.write(query.getId() + delimiter + entry.getKey()
                            + delimiter+ formatter.format(entry.getValue()) + "\n");
                }
            }
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing invertedIndex to file");
            System.exit(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
