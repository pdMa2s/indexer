import org.tartarus.snowball.ext.englishStemmer;
import java.util.Scanner;

public class MainClass {
    public static void main(String[] args){

    
        System.out.print("Please define the corpus Location: ");
        Scanner sc = new Scanner(System.in);
        String DirName = sc.nextLine();

        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);
        SimpleTokenizer st = new SimpleTokenizer();
        englishStemmer stemmer = new englishStemmer();

        while(corpusReader.hasDocument()){
            String docContent = corpusReader.processDocument();
            System.out.println(docContent);
            String[] tokens = st.tokenize(docContent);
            for (int i=0 ; i<tokens.length; i++){
                stemmer.setCurrent(tokens[i]);
                stemmer.stem();
                tokens[i] = stemmer.getCurrent();
            }
            break;
        }
    }
}
