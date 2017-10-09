package indexer;

import corpus.CorpusReader;
import tokenizer.Tokenizer;

import java.util.List;

/**
 * The purpose of this class is to create an {@link Index} that stores words occurrences in a document corpus
 * located in a certain directory.
 * This is done using a {@link CorpusReader} that will iterate over the files and return their content, after that
 * a {@link Tokenizer} is used to provide the tokens/words from that document content. Finally an {@link Index} is
 * filled with the occurrences of those tokens/words.
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see Tokenizer
 * @see Index
 * @see CorpusReader
 */
public class Indexer {
    private CorpusReader corpusReader;
    private Tokenizer tokenizer;
    private Index index;

    /**
     * Creates an {@link Index} of occurrences os words using a {@link CorpusReader}and a {@link Tokenizer}.
     * @return An {@link Index} filled with the occurrences of words in certain documents.
     */
    public Index createIndex() {

        index = new Index();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            List<String> tokens = tokenizer.tokenize(docContent);
            fillIndexWithTokens(index, tokens, corpusReader.getDocumentID());
        }
        return index;
    }

    /**
     * Gives access to the {@link Index} that was created.
     * @return The Index that was created.
     */
    public Index getIndex(){
        return index;
    }
    private void fillIndexWithTokens(Index index , List<String> tokens, int docId){
        for (String token: tokens) {
            index.addTokenOcurrence(token, docId);
        }
    }

    /**
     * Lets the user configure the {@link CorpusReader} that is going to be used in the indexation process.
     * @param corpusReader The desired {@link CorpusReader} to be used in the indexation.
     */
    public void setCorpusReader(CorpusReader corpusReader) {
        this.corpusReader = corpusReader;
    }

    /**
     * Lets the user configure the {@link Tokenizer} that is going to be used in the indexation process.
     * @param tokenizer The desired {@link Tokenizer} to be used in the indexation.
     */
    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }
}