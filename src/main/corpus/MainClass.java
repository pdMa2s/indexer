import java.util.ArrayList;
import java.util.List;
import org.tartarus.snowball.ext.englishStemmer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class MainClass {
    public static void main(String[] args) {

        String DirName = "cranfield";

        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);
        SimpleTokenizer st = new SimpleTokenizer();
        List<String> stopWordList = fillStopwordList();
        englishStemmer stemmer = new englishStemmer();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            System.out.println(docContent);
            List<String> tokens = st.tokenize(docContent);
            //tokens.removeAll(stopWordList);
            for (int i = 0; i < tokens.size(); i++) {
                stemmer.setCurrent(tokens.get(i));
                stemmer.stem();
                tokens.set(i, stemmer.getCurrent());
            }
            System.out.println(tokens);
            System.out.println(stopWordList.size());
            break;
        }
    }

    public static List<String> fillStopwordList() {

        ArrayList<String> stopWords = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("StopWordList"));
            String line = "";
            while (line != null) {
                line = br.readLine();
                stopWords.add(line);
            }
        } catch (IOException e) {
        }
        return stopWords;
    }
}

