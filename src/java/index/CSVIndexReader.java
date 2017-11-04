package src.java.index;

import org.tartarus.snowball.ext.englishStemmer;
import src.java.normalizer.Normalizer;
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
 * This implementation of {@link IndexReader} reads an {@link InvertedIndex} object from the disk, with a format similar
 * to CSV.<br>
 * Example: term, documentId:DocumentFrequency,documentId:DocumentFrequency, ...
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 * @see <a href="https://pt.wikipedia.org/wiki/Comma-separated_values">CSV</a>
 */
public class CSVIndexReader implements IndexReader {

    private Tokenizer tokenizer;
    private int corpusSize;

    /**
     *{@inheritDoc}
     */
    @Override
    public Tokenizer getTokenizer() {
        return tokenizer;
    }

    @Override
    public int getCorpusSize() {
        return corpusSize;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public void parseInvertedIndex(File indexFile, InvertedIndex index){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(indexFile));
            String text;
            text = reader.readLine();
            parseHeader(text.trim());
            while ((text = reader.readLine()) != null) {
                parsePostingsPerTerm(text, index);
            }
        }catch(FileNotFoundException e){
            System.err.println("invertedIndex file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading invertedIndex file");
            System.exit(2);
        }
    }

    public void parseDocumentAndInvertedIndexes(File indexFile, InvertedIndex invertedIndex, DocumentIndex documentIndex){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(indexFile));
            String text;
            text = reader.readLine();
            parseHeader(text.trim());
            while ((text = reader.readLine()) != null) {
                parsePostingsPerTermWithNormalization(text,invertedIndex ,documentIndex);
            }
        }catch(FileNotFoundException e){
            System.err.println("invertedIndex file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading invertedIndex file");
            System.exit(2);
        }
    }

    private void parsePostingsPerTermWithNormalization(String text,InvertedIndex invertedIndex , DocumentIndex documentIndex){
        List<Posting> postings = new ArrayList<>();
        String[] parsedPosting = text.split(",");
        String term = parsedPosting[0];
        for(int i=1; i<parsedPosting.length; i++){
            String[] post = parsedPosting[i].split(":");
            if(post.length == 2) {
                documentIndex.addTermScore(Integer.parseInt(post[0]), term, Double.parseDouble(post[1]));
                Posting tempPosting = new Posting(Integer.parseInt(post[0]), Double.parseDouble(post[1]));
                postings.add(tempPosting);
            }
            else{
                printError("ERROR while parsing invertedIndex file");
            }
        }
        invertedIndex.addTermAndPostings(term, postings);
    }

    private void parsePostingsPerTerm(String text, InvertedIndex index){
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
                printError("ERROR while parsing invertedIndex file");
            }
        }
        index.addTermAndPostings(term, postings);
    }
    private void printError(String message){
        System.err.println(message);
        System.exit(3);
    }

    private void parseHeader(String headerText){
        String[] header = headerText.split(":");
        if(header.length != 2)
            printError("ERROR while parsing invertedIndex file\nInvalid header");
        parseTokenizerType(header[0]);
        corpusSize = Integer.parseInt(header[1]);
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
