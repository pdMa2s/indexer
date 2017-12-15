package src.java.indexer;

import src.java.index.InvertedIndex;
import src.java.corpus.CorpusReader;
import src.java.normalizer.Normalizer;
import src.java.query.DocumentIndex;
import src.java.corpus.tokenizer.Tokenizer;

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
 * @see Tokenizer
 * @see InvertedIndex
 * @see CorpusReader
 * @since 09-27-2017
 */
public class Indexer {
    private CorpusReader corpusReader;
    private Tokenizer tokenizer;
    private InvertedIndex index;
    private Normalizer normalizer;
    private DocumentIndex documentIndex;
    private StringBuilder corpusFullContent;


    public InvertedIndex createIndex() {
        corpusFullContent = new StringBuilder();
        if (normalizer == null)
            return createFrequencyIndex();
        else
            return createNormalizeIndex();
    }

    /**
     * Creates an {@link InvertedIndex} of occurrences os words using a {@link CorpusReader}and a {@link Tokenizer}.
     *
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

    /**
     * Creates an {@link InvertedIndex} with the normalized weights for each document.
     * Uses a {@link Tokenizer} and a {@link CorpusReader}.
     * @return An {@link InvertedIndex} filled with the weights of terms in certain documents.
     */
    public InvertedIndex createNormalizeIndex() {

        index = new InvertedIndex();

        while (corpusReader.hasDocument()) {
            String docContent = corpusReader.processDocument();
            List<String> tokens = tokenizer.tokenize(docContent);
            corpusFullContent.append(getCleanedString(tokens));
            fillIndexWithNormalizedTerms(index, tokens, corpusReader.getDocumentID());
        }
        return index;
    }

    private String getCleanedString(List<String> tokens) {
        StringBuilder tokensText = new StringBuilder();
        for (String token : tokens) {
            tokensText.append(token).append(" ");
        }
        return tokensText.toString();
    }

    /**
     *
     * @return The {@link String} that contains the full content of the corpus.
     */
    public String getCorpusFullContent() {
        return corpusFullContent.toString();
    }

    /**
     * Gives access to the {@link InvertedIndex} that was created.
     *
     * @return The invertedIndex that was created.
     */
    public InvertedIndex getIndex() {
        return index;
    }

    private void fillIndexWithNormalizedTerms(InvertedIndex index, List<String> terms, int docId) {
        Map<String, Double> normalizedScores = normalizer.normalize(terms);
        documentIndex.addTermsPerDocID(docId, normalizedScores);
        for (String term : normalizedScores.keySet()) {
            index.addNormalizedScore(term, docId, normalizedScores.get(term));
        }
    }

    private void fillIndexWithTermOccurrences(InvertedIndex index, List<String> tokens, int docId) {
        for (String token : tokens) {
            index.addTokenOccurrence(token, docId);
        }
    }


    /**
     * Lets the user configure the {@link CorpusReader} that is going to be used in the indexation process.
     *
     * @param corpusReader The desired {@link CorpusReader} to be used in the indexation.
     */
    public void setCorpusReader(CorpusReader corpusReader) {
        this.corpusReader = corpusReader;
    }

    /**
     * Lets the user configure the {@link Tokenizer} that is going to be used in the indexation process.
     *
     * @param tokenizer The desired {@link Tokenizer} to be used in the indexation.
     */
    public void setTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }

    /**
     *
     * @param normalizer Set the {@link Normalizer}
     */
    public void setNormalizer(Normalizer normalizer) {
        this.normalizer = normalizer;
    }

    /**
     *
     * @return The number of documents in the corpus
     */
    public int getCorpusSize() {
        return corpusReader.corpusSize();
    }

    /**
     *
     * @param docIndex The {@link DocumentIndex} to be used
     */
    public void setDocumentIndex(DocumentIndex docIndex) {
        this.documentIndex = docIndex;
    }
}