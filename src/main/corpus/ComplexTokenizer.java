import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;

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


    public ComplexTokenizer() {
        this.stemmer = new englishStemmer();
        this.stopWordList = fillStopWordList();

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
        stemmize(tokens);
        return tokens;
    }


    private List<String> fillStopWordList() {

        ArrayList<String> stopWords = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("StopWordList"));
            String line = "";
            while (line != null) {
                line = br.readLine();
                stopWords.add(line);
            }
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
