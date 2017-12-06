package src.java.index;

import src.java.query.DocumentIndex;
import src.java.tokenizer.Tokenizer;

import java.io.File;

/**
 * An entity that reads an {@link InvertedIndex} from disk with a format depending on the implementation.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */public interface IndexReader {
    /**
     * @return The {@link Tokenizer} that was used to create the {@link InvertedIndex}, this information is located
     * on the header of the invertedIndex file, more precisely, the first line of the file.
     */
    Tokenizer getTokenizer();

    /**
     *
     * @return The size of the corpus that originated the index
     */
    int getCorpusSize();

    /**
     *
     * @return The tag that identifies the scoring system used to create the index.
     * @see src.java.constants.Constants
     */
    String getScoringSystem();
    /**
     * This function parses the file that contains the invertedIndex, reads the header from file which indicates the type
     * of {@link Tokenizer} used in the indexation, after that loads an {@link InvertedIndex} object with terms and their
     * respective {@link Posting} lists.
     * @param indexFile The {@link File} on disk that contains the invertedIndex.
     */
    void parseInvertedIndex(File indexFile, InvertedIndex index);

     void parseDocumentIndexFromFile(File docIndexFile, DocumentIndex docIndex);
}
