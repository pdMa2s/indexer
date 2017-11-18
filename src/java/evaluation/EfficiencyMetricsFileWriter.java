package src.java.evaluation;

import src.java.query.Query;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;


public class EfficiencyMetricsFileWriter implements EfficiencyMetricsWriter{

    private String metricFileName;
    private String delimiter = "\t";
    public EfficiencyMetricsFileWriter(String fileName){
        this.metricFileName = fileName;
    }
    @Override
    public void saveEfficiencyResults(Map<String, Double> results, List<Query> queries) {
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
        writer.write("query_id" + delimiter + "precision" + delimiter + "recall" + delimiter + "f-measure"+"\n");

        for(Query query: queries){
            System.out.println(query.getfMeasure());
            writer.write( query.getId() + delimiter +
                    formatter.format(query.getQueryPrecision()) + delimiter + formatter.format(query.getQueryRecall())
                        + delimiter + formatter.format(query.getfMeasure())+"\n");

        }
    }
    private void printGeneralMetrics(BufferedWriter writer, Map<String, Double> results, NumberFormat formatter) throws IOException {
        for(Map.Entry<String, Double> resultEntry : results.entrySet()){
            writer.write(resultEntry.getKey() + delimiter + formatter.format(resultEntry.getValue()) +"\n");
        }
        writer.write("\n");
    }
}
