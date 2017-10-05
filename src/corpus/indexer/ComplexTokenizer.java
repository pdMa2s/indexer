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

public class ComplexTokenizer implements Tokenizer{
    private SnowballStemmer stemmer;
    private List<String> stopWordList;
    private static String STOPWORDFILENAME = "StopWordList";

    public ComplexTokenizer() {
        this.stopWordList = fillStopWordList();
        this.stemmer = null;
    }
    public ComplexTokenizer(SnowballStemmer stemmer) {
        this.stopWordList = fillStopWordList();
        this.stemmer = stemmer;
    }


    @Override
    public List<String> tokenize(String docInfo) {
        List<String> tokens = Stream
                .of(docInfo)
                .map(String::toLowerCase)
                .map(w -> w.replaceAll("[^\\w\\s]+", ""))
                .map(w -> w.replaceAll("\\s+", " "))
                .map(w -> w.trim())
                .map(s -> s.split("\\s")).flatMap(Arrays::stream)
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
