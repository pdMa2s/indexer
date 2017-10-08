package indexer;

import org.tartarus.snowball.SnowballStemmer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An implementation of the interface Tokenizer, creates a collection of tokens based on a documents content.
 * Reads a set of stop words from a file and filters tokens based on that set.
 * Applying stemming is optional, if a user wishes to apply it, it must pass a Stemmer as a parameter through
 * the constructor.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 09-27-2017
 * @see Tokenizer
 * @see SnowballStemmer
 */
public class ComplexTokenizer implements Tokenizer{
    private SnowballStemmer stemmer;
    private List<String> stopWordList;
    private static String STOPWORDFILENAME = "StopWordList";

    /**
     * Constructs a ComplexTokenizer Object, loads a list of stop words.
     */
    public ComplexTokenizer() {
        this.stopWordList = fillStopWordList();
        this.stemmer = null;
    }

    /**
     * Constructs a ComplexTokenizer Object, loads a list of stop words. Using this constructor will make the tokenizer
     * use stemming.
     * @param stemmer The stemmer to be applied on the tokens.
     * @see SnowballStemmer
     */
    public ComplexTokenizer(SnowballStemmer stemmer) {
        this.stopWordList = fillStopWordList();
        this.stemmer = stemmer;
    }

    /**
     * This implementation of the tokenizer function takes the content of a document, converts its characters to
     * lower case, removes all non-alphanumeric characters, excess space characters and splits the content by
     * whitespaces.
     * After that the several tokens from the previous operations are filtered on a set os stop words.
     * If the a Stemmer was passed to the constructor a stemming process is also applied to the tokens.
     * @param docInfo A String which represents the contents of a file
     * @return A List of tokens extracted from the content os a document
     * @see List
     */
    @Override
    public List<String> tokenize(String docInfo) {
        List<String> tokens = Stream
                .of(docInfo)
                .map(w -> w.replaceAll("[^\\w\\s]+", ""))
                .map(w -> w.trim())
                .map(String::toLowerCase)
                .map(s -> s.split("\\s+")).flatMap(Arrays::stream)
                .collect(Collectors.toList());
        filterTokensFromStopWords(tokens, stopWordList);
        if(stemmer != null)
            stemmize(tokens);
        return tokens;
    }


    private List<String> fillStopWordList() {

        ArrayList<String> stopWords = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(STOPWORDFILENAME));
            String line;
            do{
                line = br.readLine();
                if(line != null)
                    stopWords.add(line.trim());
            }
            while (line != null);

        } catch (IOException e) {
            System.err.println("ERROR Loading Stop Words");
            System.exit(1);
        }
        return stopWords;
    }

    private void filterTokensFromStopWords(List<String> tokens, List<String> stopWords){
        tokens.removeAll(stopWords);
    }
    private void stemmize(List<String> tokens){
        for (int i = 0; i < tokens.size(); i++) {

            stemmer.setCurrent(tokens.get(i));
            stemmer.stem();
            tokens.set(i, stemmer.getCurrent());

        }
    }

}
