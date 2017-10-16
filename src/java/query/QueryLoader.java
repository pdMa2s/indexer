package src.java.query;


import src.java.tokenizer.Tokenizer;
import java.io.*;
import java.util.*;

public class QueryLoader implements QueryReader {

    private Scanner scannerReader = null;
    private Tokenizer tokenizer = null;
    private List<Query> queries;
    private Map<Integer, Integer> results = new HashMap<>();


    public QueryLoader(Tokenizer tokenizer){
        this.tokenizer = tokenizer;
        this.queries = new ArrayList<>();

    }

   @Override
    public List<Query> loadQueries(File queryFile){
        try {
            scannerReader = new Scanner(queryFile);
            scannerReader.useDelimiter("\\.");
            String query;
            int queryCounter = 0;
            while(scannerReader.hasNext()){
                query = scannerReader.next();
                queryCounter++;
                queries.add(new Query(queryCounter,tokenizer.tokenize(query)));
            }
        }catch(FileNotFoundException e){
            System.err.println("Query file not found!");
            System.exit(3);
        }
        return queries;
    }

}
