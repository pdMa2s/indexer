package src.java.evaluation;

import src.java.query.Query;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.java.constants.Constants.THRESHOLDDEFAULTVALUE;

public class Evaluator {

    private File relevanceFile;
    private List<Query> queries;
    private Map<Integer, Map<Integer, ArrayList<Integer>>> relevanceMatrix;
    private final String[] generalMetrics = {"MeanPrecision", "FMeasure",
            "MeanReciprocalRank", "MeanQueryLatency", "QueryThroughput"};
    private EfficiencyMetricsWriter efficiencyWriter;

    public Evaluator(String relevanceFile, List<Query> queries, EfficiencyMetricsWriter metricsWriter){
        this.relevanceFile = new File(relevanceFile);
        this.relevanceMatrix = new HashMap<>();
        this.queries = queries;
        this.efficiencyWriter = metricsWriter;
        parseRelevanceFile();
    }

    public void calculateSystemMeasures( int minimumRelevance, double threshold){
        for(Query q : queries){
            if(threshold != THRESHOLDDEFAULTVALUE)
                calculatePrecision(q, minimumRelevance);
            else
                precisionAtRank10(q, minimumRelevance);
            calculateRecall(q, minimumRelevance);
            calculateReciprocalRankPerQuery(q, minimumRelevance);
        }
        Map<String, Double> resultMap = createResultMap(queries);
        efficiencyWriter.saveEfficiencyResults(resultMap, queries);

    }

    private Map<String, Double> createResultMap(List<Query> queries){
        double meanQueryLatency = calculateMeanQueryLatency(queries);
        double queryThroughput = calculateQueryThroughput(meanQueryLatency);

        Double[] resultScores = {calculateMeanPrecision(queries), calculateFMeasure(queries),
                calculateMeanReciprocalRank(queries),meanQueryLatency, queryThroughput };
        Map<String, Double> resultMap = new HashMap<>();
        for(int i= 0; i< resultScores.length; i++){
            resultMap.put(generalMetrics[i], resultScores[i]);
        }
        return resultMap;

    }

    public void calculatePrecision(Query q, int minimumRelevance){
        double returnedRelevance = 0;

        for(int docID : q.getDocIds()){
            for(int i=minimumRelevance; i>0; i--){
                if(relevanceMatrix.get(q.getId()).get(i).contains(docID)){
                    returnedRelevance++;
                    break;
                }
            }
        }
        q.setQueryPrecision(returnedRelevance/q.getDocIds().size());
    }

   public void calculateReciprocalRankPerQuery(Query q, int minimumRelevance){
        double reciprocalRank = 0;
        boolean reciprocalFound = false;

        for(int docID : q.getSortedResults().keySet()){
            for(int i=minimumRelevance; i>0; i--){
                if(relevanceMatrix.get(q.getId()).get(i).contains(docID)){
                    reciprocalRank++;
                    reciprocalFound = true;
                    break;
                }
            }
            if(reciprocalFound)
                break;
            reciprocalRank++;
        }
        q.setReciprocalRank(1/reciprocalRank);
    }


    public void precisionAtRank10(Query q, int minimumRelevance){
        double returnedRelevance = 0;
        int count = 0;
        for(int docID : q.getSortedResults().keySet()){
            count++;
            for(int i=minimumRelevance; i>0; i--){
                if(relevanceMatrix.get(q.getId()).get(i).contains(docID)){
                    returnedRelevance++;
                    break;
                }
            }
            if(count == 10)
                break;
        }
        q.setQueryPrecision(returnedRelevance/10);
    }

    public void calculateRecall(Query q, int minimumRelevance){
        double returnedRelevance = 0;
        double totalRelevance = 0;
        for(int docID : q.getDocIds()){
            for(int i=minimumRelevance; i>0; i--){
                if(relevanceMatrix.get(q.getId()).get(i).contains(docID)){
                    returnedRelevance++;
                    break;
                }
            }
        }
        for(int i=minimumRelevance; i>0; i--){
            if(relevanceMatrix.get(q.getId()).containsKey(i))
                totalRelevance += relevanceMatrix.get(q.getId()).get(i).size();
        }
        q.setQueryRecall(returnedRelevance/totalRelevance);
    }

    public double calculateQueryThroughput(double meanQueryLatency){
        return 1000/meanQueryLatency;
    }

    public double calculateMeanPrecision(List<Query> queries){
        double totalPrecisionSum = 0;
        for(Query q : queries){
            totalPrecisionSum += q.getQueryPrecision();
        }
        return totalPrecisionSum/queries.size();
    }

    public double calculateFMeasure(List<Query> queries){
        double totalFMeasure = 0;
        for(Query q : queries){
            totalFMeasure += (2*q.getQueryPrecision()*q.getQueryRecall())/(q.getQueryPrecision()+q.getQueryRecall());
        }
        return totalFMeasure / queries.size();
    }

    public double calculateMeanReciprocalRank(List<Query> queries){
        double totalReciprocalRank = 0;
        for(Query q : queries){
            totalReciprocalRank += q.getReciprocalRank();
        }
        return totalReciprocalRank / queries.size();
    }

    public double calculateMeanQueryLatency(List<Query> queries){
        double totalQueryProcessingTime = 0;
        for(Query q : queries){
            totalQueryProcessingTime += q.getProcessingTime();
        }
        return totalQueryProcessingTime / queries.size();
    }

    public void parseRelevanceFile(){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(relevanceFile));
            String relevanceTuple;
            while ((relevanceTuple = reader.readLine()) != null) {
                fillRelevanceMatrix(relevanceTuple);
            }
        }catch(FileNotFoundException e){
            System.err.println("invertedIndex file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading invertedIndex file");
            System.exit(2);
        }
    }

    private void fillRelevanceMatrix(String relevanceTuple){
        String[] content = relevanceTuple.split("\\s+");
        Map<Integer, ArrayList<Integer>> relevancePerQuery = relevanceMatrix.get(Integer.parseInt(content[0]));
        if(relevancePerQuery != null){
            ArrayList<Integer> docList = relevancePerQuery.get(Integer.parseInt(content[2]));
            if(docList != null){
                docList.add(Integer.parseInt(content[1]));
                relevancePerQuery.put(Integer.parseInt(content[2]), docList);
            }
            else{
                ArrayList<Integer> newDoc = new ArrayList<>();
                newDoc.add(Integer.parseInt(content[1]));
                relevancePerQuery.put(Integer.parseInt(content[2]), newDoc);
            }
        }
        else{
            Map<Integer, ArrayList<Integer>> newQueryMap = new HashMap<>();
            ArrayList<Integer> newDoc = new ArrayList<>();
            newDoc.add(Integer.parseInt(content[1]));
            newQueryMap.put(Integer.parseInt(content[2]), newDoc);
            relevanceMatrix.put(Integer.parseInt(content[0]), newQueryMap);
        }
    }

    private int totalResults(List<Query> queries){
        int total = 0;
        for(Query query : queries){
            total += query.getResults().size();
        }
        return total;
    }

    @Override
    public String toString() {
        return "Evaluator{" +
                "relevanceMatrix=" + relevanceMatrix +
                '}';
    }
}
