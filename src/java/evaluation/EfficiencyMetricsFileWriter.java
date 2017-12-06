package src.java.evaluation;

import src.java.query.Query;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;


/**
 * This implementation of {@link EfficiencyMetricsHandler} writes the results of the evaluation and efficiency metrics
 * to a file.
 */
public class EfficiencyMetricsFileWriter implements EfficiencyMetricsHandler {

    private String metricFileName;
    private String delimiter = "\t";

    /**
     * Constructs a {@link EfficiencyMetricsFileWriter} object
     * @param fileName The name of the file where the results will be written into.
     */
    public EfficiencyMetricsFileWriter(String fileName){
        this.metricFileName = fileName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void handleEfficiencyResults(Map<String, Double> results, List<Query> queries) {
        BufferedWriter writer;
        FileWriter fw;

        try {
            fw = new FileWriter(metricFileName);
            writer = new BufferedWriter(fw);
            NumberFormat formatter = new DecimalFormat("#0.00000");
            printGeneralMetrics(writer, results, formatter);
            printMetricsForQueries(writer, queries, formatter);
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing efficiency metric results to file");
            System.exit(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printMetricsForQueries(BufferedWriter writer,List<Query> queries, NumberFormat formatter) throws IOException {
        writer.write("query_id" + delimiter + "precision"+ delimiter+ "precisionRank10" + delimiter + "recall" + delimiter + "f-measure"+"\n");

        for(Query query: queries){
            writer.write( query.getId() + delimiter +
                    formatter.format(query.getQueryPrecision()) +delimiter+ query.getQueryPrecisionAtRank10() +delimiter + formatter.format(query.getQueryRecall())
                        + delimiter + formatter.format(query.geTfMeasure())+"\n");

        }
    }
    private void printGeneralMetrics(BufferedWriter writer, Map<String, Double> results, NumberFormat formatter) throws IOException {
        for(Map.Entry<String, Double> resultEntry : results.entrySet()){
            writer.write(resultEntry.getKey() + delimiter + formatter.format(resultEntry.getValue()) +"\n");
        }
        writer.write("\n");
    }
}
