import java.util.List;

public class MainClass {
    public static void main(String[] args){

    
        String DirName = "cranfield";

        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);
        SimpleTokenizer st = new SimpleTokenizer();
        while(corpusReader.hasDocument()){
            String docContent = corpusReader.processDocument();
            System.out.println(docContent);
            List<String> tokens = st.tokenize(docContent);
            for(String token : tokens){
                System.out.println(token);
            }
            break;
        }
    }
}
