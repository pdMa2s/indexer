package src.java.query.relevancefeedback;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class RevelevanceReader implements RelevanceFileReader{
    @Override
    public RelevanceIndex parseRelevanceFile(String filePath) {
        RelevanceIndex revIndex = new RelevanceIndex();
        readFile(revIndex, filePath);
        return revIndex;
    }

    private void readFile(RelevanceIndex revIndex,String filePath){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(filePath));
            String relevanceTuple;
            while ((relevanceTuple = reader.readLine()) != null) {
                String[] lineContent = relevanceTuple.split("\\s+");
                int query = Integer.parseInt(lineContent[0]);
                int docId = Integer.parseInt(lineContent[1]);
                int relevance = Integer.parseInt(lineContent[2]);
                revIndex.addRelevanceScore(query, docId, relevance);
            }
        }catch(FileNotFoundException e){
            System.err.println("Relevance file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading relevancefeedback file");
            System.exit(2);
        }
    }
}
