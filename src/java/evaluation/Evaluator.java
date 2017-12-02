package src.java.evaluation;

import src.java.query.Query;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for loading a file that contains the relevancefeedback scores of the corpus and calculate
 * the results for several evaluation and efficiency metrics such as mean precision, mean precision at rank 10,
 * mean reciprocal rank mean query latency, mean query throughput, mean f-measure and mean recall.
 */
public class Evaluator {

    private File relevanceFile;
    private List<Query> queries;
    private Map<Integer, Map<Integer, ArrayList<Integer>>> relevanceMatrix;
    private final String[] generalMetrics = {"MeanPrecision", "MeanPrecisionAtRank10",
            "MeanReciprocalRank", "MeanQueryLatency", "MeanQueryThroughput",
            "MeanFMeasure", "MeanRecall"};
    private EfficiencyMetricsHandler efficiencyHandler;

    /**
     * Constructs an {@link Evaluator} object
     * @param relevanceFile The name of the file that contains the relevancefeedback scores
     * @param queries A {@link List} of {@link Query} objects to store their respective results
     * @param metricsHandler A handler for the results
     */
    public Evaluator(String relevanceFile, List<Query> queries, EfficiencyMetricsHandler metricsHandler){
        this.relevanceFile = new File(relevanceFile);
        this.relevanceMatrix = new HashMap<>();
        this.queries = queries;
        this.efficiencyHandler = metricsHandler;
        parseRelevanceFile();
    }

    /**
     * Calculates the value of the several metrics, then asks the handler to take care of the results.
     * @param minimumRelevance The minimum relevancefeedback to be taken into account.
     */
    public void calculateSystemMeasures( int minimumRelevance){
        checkMinimumRelevanceScore(minimumRelevance);
        for(Query q : queries){
            calculatePrecision(q, minimumRelevance);
            precisionAtRank10(q, minimumRelevance);
            calculateRecall(q, minimumRelevance);
            calculateReciprocalRankPerQuery(q, minimumRelevance);
            calculateFMeasure(q);
        }
        Map<String, Double> resultMap = createResultMap(queries);
        efficiencyHandler.handleEfficiencyResults(resultMap, queries);

    }

    private Map<String, Double> createResultMap(List<Query> queries){
        double meanQueryLatency = calculateMeanQueryLatency(queries);
        double queryThroughput = calculateQueryThroughput(meanQueryLatency);

        Double[] resultScores = {calculateMeanPrecision(queries),calculateMeanPrecisionAtRank10(queries),
                calculateMeanReciprocalRank(queries),meanQueryLatency, queryThroughput,
                calculateMeanFMeasure(queries), calculateMeanRecall(queries) };
        Map<String, Double> resultMap = new HashMap<>();
        for(int i= 0; i< resultScores.length; i++){
            resultMap.put(generalMetrics[i], resultScores[i]);
        }
        return resultMap;
    }

    private void calculatePrecision(Query q, int minimumRelevance){
        double returnedRelevance = 0;

        for(int docID : q.getDocIds()){
            for(int i=minimumRelevance; i>0; i--){
                if(relevanceMatrix.get(q.getId()).get(i) != null) {
                    if (relevanceMatrix.get(q.getId()).get(i).contains(docID)) {
                        returnedRelevance++;
                        break;
                    }
                }
            }
        }
        int nDocs = q.getDocIds().size();
        if(nDocs == 0)
            q.setQueryPrecision(0);
        else
            q.setQueryPrecision(returnedRelevance/nDocs);
    }

   private void calculateReciprocalRankPerQuery(Query q, int minimumRelevance){
        int reciprocalRank = 0;
        boolean reciprocalFound = false;

        for(int docID : q.getSortedResults().keySet()){
            for(int i=minimumRelevance; i>0; i--){
                if(relevanceMatrix.get(q.getId()).get(i) != null) {
                    if (relevanceMatrix.get(q.getId()).get(i).contains(docID)) {
                        reciprocalRank++;
                        reciprocalFound = true;
                        break;
                    }
                }
            }
            if(reciprocalFound)
                break;
            reciprocalRank++;
        }
        if(reciprocalRank == 0)
            q.setReciprocalRank(0);
        else
            q.setReciprocalRank(1/reciprocalRank);
    }

    private void calculateFMeasure(Query q){
       double precision = q.getQueryPrecision();
       double recall = q.getQueryRecall();
       if(precision == 0 && recall == 0)
           q.setfMeasure(0);
       else
           q.setfMeasure((2*precision*recall)/(precision+recall));
    }

    private void precisionAtRank10(Query q, int minimumRelevance){
        double returnedRelevance = 0;
        int count = 0;
        for(int docID : q.getSortedResults().keySet()){
            count++;
            for(int i=minimumRelevance; i>0; i--) {
                if(relevanceMatrix.get(q.getId()).get(i) != null){
                    if (relevanceMatrix.get(q.getId()).get(i).contains(docID)) {
                        returnedRelevance++;
                        break;
                    }
                }
            }
            if(count == 10)
                break;
        }
        if(count == 0)
            q.setQueryPrecisionAtRank10(0);
        else
            q.setQueryPrecisionAtRank10(returnedRelevance/count);
    }

    private void calculateRecall(Query q, int minimumRelevance){
        double returnedRelevance = 0;
        double totalRelevance = 0;
        for(int docID : q.getDocIds()){
            for(int i=minimumRelevance; i>0; i--){
                if(relevanceMatrix.get(q.getId()).get(i) != null) {
                    if (relevanceMatrix.get(q.getId()).get(i).contains(docID)) {
                        returnedRelevance++;
                        break;
                    }
                }
            }
        }
        for(int i=minimumRelevance; i>0; i--){
            if(relevanceMatrix.get(q.getId()).containsKey(i))
                totalRelevance += relevanceMatrix.get(q.getId()).get(i).size();
        }

        if(totalRelevance == 0)
            q.setQueryRecall(0);
        else
            q.setQueryRecall(returnedRelevance/totalRelevance);
    }

    private double calculateQueryThroughput(double meanQueryLatency){
        return 1000/meanQueryLatency;
    }

    private double calculateMeanPrecision(List<Query> queries){
        double totalPrecisionSum = 0;
        for(Query q : queries){
            totalPrecisionSum += q.getQueryPrecision();
        }
        return queries.size() != 0 ? totalPrecisionSum/queries.size() : 0;
    }
    private double calculateMeanFMeasure(List<Query> queries){
        double totalFMeasure = 0;
        for(Query q : queries){
            totalFMeasure += q.getfMeasure();
        }
        return queries.size() != 0 ? totalFMeasure/queries.size() : 0;
    }

    private double calculateMeanPrecisionAtRank10(List<Query> queries){
        double totalPrecisionSum = 0;
        for(Query q : queries){
            totalPrecisionSum += q.getQueryPrecisionAtRank10();
        }
        return queries.size() != 0 ? totalPrecisionSum/queries.size() : 0;
    }

    private double calculateMeanReciprocalRank(List<Query> queries){
        double totalReciprocalRank = 0;
        for(Query q : queries){
            totalReciprocalRank += q.getReciprocalRank();
        }
        return queries.size() != 0 ? totalReciprocalRank / queries.size() : 0;
    }
    private double calculateMeanRecall(List<Query> queries){
        double totalRecall = 0;
        for(Query q : queries){
            totalRecall += q.getQueryRecall();
        }
        return queries.size() != 0 ? totalRecall / queries.size() : 0;
    }

    private double calculateMeanQueryLatency(List<Query> queries){
        double totalQueryProcessingTime = 0;
        for(Query q : queries){
            totalQueryProcessingTime += q.getProcessingTime();
        }
        return queries.size() != 0 ? totalQueryProcessingTime / queries.size() : 0;
    }

    private void parseRelevanceFile(){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(relevanceFile));
            String relevanceTuple;
            while ((relevanceTuple = reader.readLine()) != null) {
                fillRelevanceMatrix(relevanceTuple);
            }
        }catch(FileNotFoundException e){
            System.err.println("Relevance file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading relevancefeedback file");
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

    private void checkMinimumRelevanceScore(int minScore){
        if(minScore < 1 || minScore > 4){
            System.err.println("Invalid relevancefeedback Score");
            System.exit(3);
        }

    }

    @Override
    public String toString() {
        return "Evaluator{" +
                "relevanceMatrix=" + relevanceMatrix +
                '}';
    }
}
