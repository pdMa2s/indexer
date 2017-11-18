package src.java.evaluation;

import src.java.query.Query;

import java.io.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class EfficencyMetricsFileWriter implements EfficiencyMetricsWriter{

    private String metricFileName;

    public EfficencyMetricsFileWriter(String fileName){
        this.metricFileName = fileName;
    }
    @Override
    public void saveEfficiencyResults(Map<String, Double> results, List<Query> queries) {
        BufferedWriter writer;
        FileWriter fw;
        String delimiter = "\t";
        try {
            fw = new FileWriter(metricFileName);
            writer = new BufferedWriter(fw);
            writer.write("query_id" + delimiter + "precision" + delimiter + "recall" + "\n");
            NumberFormat formatter = new DecimalFormat("#0.00000");
            for(Query query: queries){
                for(Map.Entry<Integer, Double> entry: query.getSortedResults().entrySet()){

                    writer.write(query.getId() + delimiter + entry.getKey()
                            + delimiter+ formatter.format(entry.getValue()) + "\n");
                }
                query.clearResults();
            }
            writer.close();
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing invertedIndex to file");
            System.exit(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void printGeneralMetrics(BufferedWriter writer, Map<String, Double> results){
        for(Map.Entry<String, Double> resultEntry : results.entrySet()){
            //
        }
    }
}
