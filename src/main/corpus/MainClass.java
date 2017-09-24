import java.util.List;
import org.tartarus.snowball.ext.englishStemmer;
public class MainClass {
    public static void main(String[] args){

    
        String DirName = "cranfield";

        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);
        SimpleTokenizer st = new SimpleTokenizer();
        while(corpusReader.hasDocument()){
            String docContent = corpusReader.processDocument();
            System.out.println(docContent);
            List<String> tokens = st.tokenize(docContent);
            for(int i = 0; i < tokens.size(); i++){

                englishStemmer stemmer = new englishStemmer();

                stemmer.setCurrent(tokens.get(i));
                stemmer.stem();
                tokens.set(i,stemmer.getCurrent());
            }
            System.out.println(tokens);
            break;
        }
    }
}
