import org.tartarus.snowball.ext.englishStemmer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args) {


        System.out.print("Please define the corpus Location: ");
        Scanner sc = new Scanner(System.in);
        String DirName = sc.nextLine();

        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);
        SimpleTokenizer st = new SimpleTokenizer();
        englishStemmer stemmer = new englishStemmer();
        stopWordList[] =fillStopwordList();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            System.out.println(docContent);
            String[] tokens = st.tokenize(docContent);
            for (int i = 0; i < tokens.length; i++) {
                stemmer.setCurrent(tokens[i]);
                stemmer.stem();
                tokens[i] = stemmer.getCurrent();
            }
            break;
        }
    }

    public List<String> fillStopwordList() throws IOException {

        BufferedReader br = new BufferedReader(new FileReader("StopWordList"));
        StringBuilder sb = new StringBuilder();
        String line = br.readLine();

        while (line != null) {
            sb.append(line);
            line = br.readLine();
        }
        return sb.toString();
    }
}

