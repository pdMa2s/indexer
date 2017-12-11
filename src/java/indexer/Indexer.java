package src.java.indexer;

import src.java.index.InvertedIndex;
import src.java.corpus.CorpusReader;
import src.java.normalizer.Normalizer;
import src.java.query.DocumentIndex;
import src.java.tokenizer.Tokenizer;

import java.util.List;
import java.util.Map;

/**
 * The purpose of this class is to create an {@link InvertedIndex} that stores words occurrences in a document corpus
 * located in a certain directory.
 * This is done using a {@link CorpusReader} that will iterate over the files and return their content, after that
 * a {@link Tokenizer} is used to provide the tokens/words from that document content. Finally an {@link InvertedIndex} is
 * filled with the occurrences of those tokens/words.
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see Tokenizer
 * @see InvertedIndex
 * @see CorpusReader
 */
public class Indexer {
    private CorpusReader corpusReader;
    private Tokenizer tokenizer;
    private InvertedIndex index;
    private Normalizer normalizer;
    private DocumentIndex documentIndex;
    private StringBuilder corpusFullCOntent;


    public InvertedIndex createIndex() {
        corpusFullCOntent = new StringBuilder();
        if(normalizer == null)
            return createFrequencyIndex();
        else
            return createNormalizeIndex();
    }
        /**
         * Creates an {@link InvertedIndex} of occurrences os words using a {@link CorpusReader}and a {@link Tokenizer}.
         * @return An {@link InvertedIndex} filled with the occurrences of words in certain documents.
         */
    public InvertedIndex createFrequencyIndex() {

        index = new InvertedIndex();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            List<String> tokens = tokenizer.tokenize(docContent);
            fillIndexWithTermOccurrences(index, tokens, corpusReader.getDocumentID());
        }
        return index;
    }

    public InvertedIndex createNormalizeIndex() {

        index = new InvertedIndex();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            List<String> tokens = tokenizer.tokenize(docContent);
            corpusFullCOntent.append(getCleanedString(tokens));
            fillIndexWithNormalizedTerms(index, tokens, corpusReader.getDocumentID());
        }
        return index;
    }
    public String getCleanedString(List<String> tokens){
        StringBuilder tokensText = new StringBuilder();
        for(String token:tokens){
            tokensText.append(token).append(" ");
        }
        return tokensText.toString();
    }
    public String getCorpusFullContent() {
        return corpusFullCOntent.toString();
    }
    /**
     * Gives access to the {@link InvertedIndex} that was created.
     * @return The invertedIndex that was created.
     */
    public InvertedIndex getIndex(){
        return index;
    }

    private void fillIndexWithNormalizedTerms(InvertedIndex index , List<String> terms, int docId){
        Map<String, Double> normalizedScores = normalizer.normalize(terms);
        documentIndex.addTermsPerDocID(docId, normalizedScores);
        for(String term : normalizedScores.keySet()){
            index.addNormalizedScore(term, docId, normalizedScores.get(term));
        }
    }

    private void fillIndexWithTermOccurrences(InvertedIndex index , List<String> tokens, int docId){
        for (String token: tokens) {
            index.addTokenOccurrence(token, docId);
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

    public void setNormalizer(Normalizer normalizer) {
        this.normalizer = normalizer;
    }

    public int getCorpusSize(){
        return corpusReader.corpusSize();
    }

    public void setDocumentIndex(DocumentIndex docIndex) {this.documentIndex = docIndex; }
}