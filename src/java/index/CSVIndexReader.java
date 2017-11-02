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

/**
 * This implementation of {@link IndexReader} reads an {@link Index} object from the disk, with a format similar
 * to CSV.<br>
 * Example: term, documentId:DocumentFrequency,documentId:DocumentFrequency, ...
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 * @see <a href="https://pt.wikipedia.org/wiki/Comma-separated_values">CSV</a>
 */
public class CSVIndexReader implements IndexReader {

    private Index index;
    private Tokenizer tokenizer;

    public CSVIndexReader(){
        index = new Index();
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public Index parseToIndex(File indexFile){
        BufferedReader reader;
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

    public Index parseToIndexWithNormalization(File indexFile, Normalizer nm){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(indexFile));
            String text;
            text = reader.readLine();
            parseTokenizerType(text.trim());
            while ((text = reader.readLine()) != null) {
                parsePostingsPerTermWithNormalization(text, nm);
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

    private void parsePostingsPerTermWithNormalization(String text, Normalizer nm){
        List<Posting> postings = new ArrayList<>();
        String[] parsedPosting = text.split(",");
        String term = parsedPosting[0];
        nm.addTermDocFreq(term, parsedPosting.length-1);
        for(int i=1; i<parsedPosting.length; i++){
            String[] post = parsedPosting[i].split(":");
            if(post.length == 2) {
                nm.addDirVectorOccurencce(Integer.parseInt(post[0]), Double.parseDouble(post[1]), term);
                Posting tempPosting = new Posting(Integer.parseInt(post[0]), Integer.parseInt(post[1]));
                postings.add(tempPosting);
            }
            else{
                printError();
            }
        }
        index.addTermAndPostings(term, postings);
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
