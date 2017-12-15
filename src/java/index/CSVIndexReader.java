package src.java.index;

import org.tartarus.snowball.ext.englishStemmer;
import src.java.normalizer.Vector;
import src.java.query.DocumentIndex;
import src.java.corpus.tokenizer.ComplexTokenizer;
import src.java.corpus.tokenizer.SimpleTokenizer;
import src.java.corpus.tokenizer.Tokenizer;

import java.io.*;

import java.util.Set;
import java.util.TreeSet;

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
    private String delimiter = ":";
    private Tokenizer tokenizer;
    private int corpusSize;
    private String scoringSystem;

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
    public int getCorpusSize() {
        return corpusSize;
    }

    /**
     *{@inheritDoc}
     */
    @Override
    public String getScoringSystem() {
        return scoringSystem;
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

    public void parseDocumentIndexFromFile(File docIndexFile, DocumentIndex docIndex){
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(docIndexFile));
            String text;
            while ((text = reader.readLine()) != null) {
                parseTermsPerDocument(text, docIndex);
            }
        }catch(FileNotFoundException e){
            System.err.println("invertedIndex file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading invertedIndex file");
            System.exit(2);
        }
    }

    private void parseTermsPerDocument(String text, DocumentIndex docIndex){
        String[] parsedTerms = text.split(",");
        int docID = Integer.parseInt(parsedTerms[0]);
        Vector newVector = new Vector();
        for(int i=1; i<parsedTerms.length; i++){
            String[] term = parsedTerms[i].split(delimiter);
            if(term.length == 2) {
                newVector.put(term[0], Double.parseDouble(term[1]));
            }
            else{
                printError("ERROR while parsing documentIndex file");
            }
        }
        docIndex.addVector(docID, newVector);
    }

    private void parsePostingsPerTerm(String text, InvertedIndex index){
        Set<Posting> postings = new TreeSet<>();
        String[] parsedPosting = text.split(",");
        String term = parsedPosting[0];
        for(int i=1; i<parsedPosting.length; i++){
            String[] post = parsedPosting[i].split(delimiter);
            if(post.length == 2) {
                Posting tempPosting = new Posting(Integer.parseInt(post[0]), Double.parseDouble(post[1]));
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
        String[] header = headerText.split(delimiter);
        if(header.length != 3)
            printError("ERROR while parsing invertedIndex file\nInvalid header");
        scoringSystem = header[0];
        parseTokenizerType(header[1]);
        corpusSize = Integer.parseInt(header[2]);

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
