package src.java.queryProcessingUnits;

import src.java.indexer.Index;
import src.java.indexer.Posting;
import src.java.tokenizer.Tokenizer;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooleanQueryProcessor implements QueryProcessor {
    private File contentFile;
    private BufferedReader reader = null;
    private Tokenizer tokenizer = null;
    private List<String> queryTokens = null;
    private String queryMethod = null;
    private Map<Integer, Integer> results = new HashMap<>();
    private File queryResultsFileName;
    private int queryCounter;
    Index idx = null;

    public BooleanQueryProcessor(Tokenizer tokenizer, String queryMethod, Index idx, File queryResultsFileName){
        this.tokenizer = tokenizer;
        this.queryMethod = queryMethod;
        this.idx = idx;
        this.queryResultsFileName = queryResultsFileName;
        this.queryCounter = 0;
        saveQueryResultsToFile();
    }

    public void open(File file){
        this.contentFile = file;
    }

    public void processQueries(){
        try {
            reader = new BufferedReader(new FileReader(contentFile));
            String text = null;
            while ((text = reader.readLine()) != null) {
                queryCounter++;
                queryTokens = tokenizer.tokenize(text);
                processQuery(queryTokens);
                saveQueryResultsToFile();
            }
        }catch(FileNotFoundException e){} catch(IOException e){}
    }

    public void saveQueryResultsToFile(){
        FileWriter writer = null;
        try {
            writer = new FileWriter(queryResultsFileName.getAbsoluteFile(), true);
            if(queryCounter == 0) {
                writer.write("QueryID" + "   " + "Doc_Id" + "   " + "Doc_Score" + "\n");
            }
            else {
                for (Integer docID : results.keySet()) {
                    writer.write(String.valueOf(queryCounter) + "            " + docID + "          "  + results.get(docID) + "\n");
                }
            }
            writer.close();
        } catch ( IOException e ) {
            System.err.println("ERROR: Writing query results to file");
            System.exit(1);
        }
    }

    public void processQuery(List<String> tokens){
        Map<Integer, Integer> tempMap = new HashMap();
        if(queryMethod.equals("1")){
            for(String token : tokens) {
                List<Posting> postings = idx.getTokenList(token);
                if (postings != null) {
                    for (Posting pst : postings) {
                        if (tempMap.get(pst.getDocID()) == null)
                            tempMap.put(pst.getDocID(), 1);
                        else
                            tempMap.put(pst.getDocID(), tempMap.get(pst.getDocID()) + 1);
                    }
                }
            }
        }else{
            for(String token : tokens){
                List<Posting> postings = idx.getTokenList(token);
                if (postings != null) {
                    for (Posting pst : postings) {
                        if (tempMap.get(pst.getDocID()) == null)
                            tempMap.put(pst.getDocID(), pst.getTermFreq());
                        else
                            tempMap.put(pst.getDocID(), tempMap.get(pst.getDocID()) + pst.getTermFreq());
                    }
                }
            }
        }
        results = tempMap;
    }
}
