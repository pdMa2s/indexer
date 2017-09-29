import java.util.ArrayList;
import java.util.List;


public class Indexer {

    public static void main(String[] args) {

        //String DirName = "cranfield";
        String DirName = args[0];
        long startTime = System.currentTimeMillis();
        CorpusReader corpusReader = new DirIteratorCorpusReader(DirName);

        Tokenizer st = getTokenizer(args[1]);

        Index index;
        List<Index> smallIndexes = new ArrayList<>();
        int count = 0;

        while (corpusReader.hasDocument()) {
            if(count % 50 == 0) {
                smallIndexes.add(new Index());
            }
            String docContent = corpusReader.processDocument();
            List<String> tokens = st.tokenize(docContent);
            fillIndexWithStemmizedTokens(smallIndexes.get(smallIndexes.size()-1), tokens, corpusReader.getDocumentID());
            System.out.println(count);
            count++;
        }
        index = mergeIndexes(smallIndexes);
        //System.out.println(index);
        System.out.println("Vocabulary size: " +index.vocabularySize());
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.println(elapsedTime);
    }

    static void fillIndexWithStemmizedTokens(Index index , List<String> tokens, int docId){
        for (int i = 0; i < tokens.size(); i++) {
            index.addTokenOcurrence(tokens.get(i), docId);
        }
    }

    static Index mergeIndexes(List<Index> indexes){
        Index superIndex = new Index();
        for(Index i : indexes){
            superIndex.mergeIndex(i);
        }
        return superIndex;
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