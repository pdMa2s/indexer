import java.util.ArrayList;
import java.util.List;

import org.tartarus.snowball.SnowballStemmer;
import org.tartarus.snowball.ext.englishStemmer;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Indexer {

    public static void main(String[] args) {

        //String DirName = "cranfield";
        String DirName = args[0]; 

        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);
        Tokenizer st = getTokenizer(args[1]);
        List<String> stopWordList = fillStopWordList();
        englishStemmer stemmer = new englishStemmer();
        Index index = new Index();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            List<String> tokens = st.tokenize(docContent);

            filterStopWordsFromTokens(tokens, stopWordList);
            fillIndexWithStemmizedTokens(index,stemmer, tokens, corpusReader.getDocumentID());
            System.out.println(tokens);
            break;
        }
        //System.out.println(index);
    }

    static List<String> fillStopWordList() {

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
    static void fillIndexWithStemmizedTokens(Index index ,SnowballStemmer stemmer, List<String> tokens, int docId){

        for (int i = 0; i < tokens.size(); i++) {

            stemmer.setCurrent(tokens.get(i));
            stemmer.stem();
            tokens.set(i, stemmer.getCurrent());
            index.addTokenOcurrence(tokens.get(i), docId);

        }

    }

    static void filterStopWordsFromTokens(List<String> tokens, List<String> stopWords){
        tokens.removeAll(stopWords);
    }

    static Tokenizer getTokenizer(String className){
        Class classTemp = null;
        Tokenizer st = null;

        try {
            classTemp = Class.forName(className);
            st = (Tokenizer) classTemp.newInstance();
        }
        catch(ClassNotFoundException e) {System.out.println("Class not found");}
        catch(InstantiationException a){}
        catch(IllegalAccessException e){}
        return st;
    }
}