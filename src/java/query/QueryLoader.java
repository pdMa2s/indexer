package src.java.query;


import src.java.corpus.tokenizer.Tokenizer;
import java.io.*;
import java.util.*;

/**
 * An implementation of {@link QueryReader}. Reads a query for each line of the file.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public class QueryLoader implements QueryReader {

    private List<Query> queries;

    /**
     * Constructs a {@link QueryLoader} object.
     */
    public QueryLoader(){
        this.queries = new ArrayList<>();
    }

    /**
     * {@inheritDoc}
     */
   @Override
    public List<Query> loadQueries(File queryFile, Tokenizer tokenizer){
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
