package src.java.evaluation;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Evaluator {

    private File relevanceFile;
    private Map<Integer, Map<Integer, ArrayList<Integer>>> relevanceMatrix;

    public Evaluator(File relevanceFile){
        this.relevanceFile = relevanceFile;
        this.relevanceMatrix = new HashMap<>();
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

    public void fillRelevanceMatrix(String relevanceTuple){
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
}
