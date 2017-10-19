package src.java.index;

import org.tartarus.snowball.ext.englishStemmer;
import src.java.tokenizer.ComplexTokenizer;
import src.java.tokenizer.SimpleTokenizer;
import src.java.tokenizer.Tokenizer;

import java.io.*;
import java.util.ArrayList;

import java.util.List;

import static src.java.constants.Constants.COMPLEXTOKENIZER;
import static src.java.constants.Constants.COMPLEXTOKENIZERSTEMMING;
import static src.java.constants.Constants.SIMPLETOKENIZER;

public class CSVIndexReader implements IndexReader {

    private Index index;
    private Tokenizer tokenizer;
    private BufferedReader reader;
    public CSVIndexReader(){
        index = new Index();
    }

    @Override
    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    @Override
    public Index parseToIndex(File indexFile){
        try{
            reader = new BufferedReader(new FileReader(indexFile));
            String text;
            text = reader.readLine();
            parseTokenizerType(text.trim());
            while ((text = reader.readLine()) != null) {
                parsePostingsPerTerm(text);
            }
        }catch(FileNotFoundException e){
            System.err.println("index file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading index file");
            System.exit(2);
        }
        return index;
    }

    private void parsePostingsPerTerm(String text){
        List<Posting> postings = new ArrayList<>();
        String[] parsedPosting = text.split(",");
        String term = parsedPosting[0];
        for(int i=1; i<parsedPosting.length; i++){
            String[] post = parsedPosting[i].split(":");
            if(post.length == 2) {
                Posting tempPosting = new Posting(Integer.parseInt(post[0]), Integer.parseInt(post[1]));
                postings.add(tempPosting);
            }
            else{
                printError();
            }
        }
        index.addTermAndPostings(term, postings);
    }
    private void printError(){
        System.err.println("ERROR while parsing index file");
        System.exit(3);
    }
    private void parseTokenizerType(String header){
        switch (header){
            case SIMPLETOKENIZER:
                tokenizer = new SimpleTokenizer();
                break;
            case COMPLEXTOKENIZER:
                tokenizer = new ComplexTokenizer();
                break;
            case COMPLEXTOKENIZERSTEMMING:
                tokenizer = new ComplexTokenizer(new englishStemmer());
                break;
            default:
                System.err.println("ERROR: Unknown Tokenizer type on header");
                System.exit(1);
        }
    }
}
