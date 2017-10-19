package src.java.index;

import src.java.tokenizer.Tokenizer;

import java.io.File;

/**
 * An entity that reads an {@link Index} from disk with a format depending on the implementation.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */public interface IndexReader {
    /**
     * @return The {@link Tokenizer} that was used to create the {@link Index}, this information is located
     * on the header of the index file, more precisely, the first line of the file.
     */
    Tokenizer getTokenizer();

    /**
     * This function parses the file that contains the index, reads the header from file which indicates the type
     * of {@link Tokenizer} used in the indexation, after that loads an {@link Index} object with terms and their
     * respective {@link Posting} lists.
     * @param indexFile The {@link File} on disk that contains the index.
     * @return The {@link Index} that was represented of the @param indexFile
     */
    Index parseToIndex(File indexFile);
}
