package src.java.query;


import src.java.tokenizer.Tokenizer;
import java.io.*;
import java.util.*;

public class QueryLoader implements QueryReader {

    private Tokenizer tokenizer;
    private List<Query> queries;


    public QueryLoader(Tokenizer tokenizer){
        this.tokenizer = tokenizer;
        this.queries = new ArrayList<>();

    }

   @Override
    public List<Query> loadQueries(File queryFile){
        try {
            InputStream inputStream = new FileInputStream(queryFile);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String query;
            int queryCounter = 0;
            while((query = bf.readLine()) != null){
                queryCounter++;
                queries.add(new Query(queryCounter,tokenizer.tokenize(query)));
            }
        }catch(FileNotFoundException e){
            System.err.println("Query file not found!");
            System.exit(3);
        } catch (IOException e) {
            e.printStackTrace();
        }
       return queries;
    }

}
