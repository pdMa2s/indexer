package src.java.index;

import src.java.normalizer.Normalizer;
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
     * on the header of the index file, more precisely, the first line of the file.
     */
    Tokenizer getTokenizer();

    /**
     * This function parses the file that contains the index, reads the header from file which indicates the type
     * of {@link Tokenizer} used in the indexation, after that loads an {@link InvertedIndex} object with terms and their
     * respective {@link Posting} lists.
     * @param indexFile The {@link File} on disk that contains the index.
     * @return The {@link InvertedIndex} that was represented of the @param indexFile
     */
    void parseInvertedIndex(File indexFile, InvertedIndex index);
    void parseDocumentAndInvertedIndexes(File indexFile, InvertedIndex invertedIndex, DocumentIndex documentIndex);
}
