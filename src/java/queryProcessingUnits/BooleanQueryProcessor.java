package src.java.queryProcessingUnits;

import src.java.tokenizer.Tokenizer;

import java.io.*;
import java.util.List;

public class BooleanQueryProcessor implements QueryProcessor {
    private File contentFile;
    private BufferedReader reader = null;
    private Tokenizer tokenizer = null;
    private List<String> queryTokens = null;

    public BooleanQueryProcessor(Tokenizer tokenizer){ this.tokenizer = tokenizer; }

    public void open(File file){
        this.contentFile = file;
    }

    public void processQueries(){
        try {
            reader = new BufferedReader(new FileReader(contentFile));
            String text = null;
            while ((text = reader.readLine()) != null) {
                queryTokens = tokenizer.tokenize(text);
                processQuery(queryTokens);
            }
        }catch(FileNotFoundException e){} catch(IOException e){}
    }

    public void processQuery(List<String> tokens){
    }
}
