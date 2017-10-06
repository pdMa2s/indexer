package indexer;

import java.util.List;


public class Indexer {
    private CorpusReader corpusReader;
    private Tokenizer tokenizer;
    private Index index;

    public Index createIndex() {

        index = new Index();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            List<String> tokens = tokenizer.tokenize(docContent);
            fillIndexWithTokens(index, tokens, corpusReader.getDocumentID());
        }
        return index;
    }

    public Index getIndex(){
        return index;
    }
    private void fillIndexWithTokens(Index index , List<String> tokens, int docId){
        for (String token: tokens) {
            index.addTokenOcurrence(token, docId);
        }
    }

    public void setCorpusReader(CorpusReader corpusReader) {
        this.corpusReader = corpusReader;
    }

    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
}