import java.util.ArrayList;
import java.util.List;


public class Indexer {

    public static void main(String[] args) {

        String DirName = args[0];
        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);

        Tokenizer st = getTokenizer(args[1]);
        Index index = new Index();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            List<String> tokens = st.tokenize(docContent);
            fillIndexWithTokens(index, tokens, corpusReader.getDocumentID());
        }
        //System.out.println(index);
        System.out.println("Vocabulary size: " +index.vocabularySize());
        System.out.println("Ten First terms in document 1 with alphabetical order: " + index.getTop10Terms(1));
        System.out.println("Ten terms with the higher doc frequency: " + index.getTopFreqTerms());
    }

    static void fillIndexWithTokens(Index index , List<String> tokens, int docId){
        for (int i = 0; i < tokens.size(); i++) {
            index.addTokenOcurrence(tokens.get(i), docId);
        }
    }

    static Tokenizer getTokenizer(String className){
        Class classTemp = null;
        Tokenizer st = null;

        try {
            classTemp = Class.forName(className);
            st = (Tokenizer) classTemp.newInstance();
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            System.err.println("ERROR Loading Tokenizer");
            System.exit(1);
        }
        return st;
    }
}