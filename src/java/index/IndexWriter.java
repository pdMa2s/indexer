package src.java.index;

/**
 * An entity that writes an {@link InvertedIndex} on disk with a format depending on the implementation.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public interface IndexWriter {
    /**
     * The implementation of this function should write in the first line of the file an header
     * that only contains a tag that identifies the {@link src.java.tokenizer.Tokenizer} that was used
     * to create the @param invertedIndex.
     * @see src.java.constants.Constants
     * After that, the @param invertedIndex itself should be written.
     * @param fileName The of the file where the invertedIndex will be written.
     * @param index The {@link InvertedIndex} object to be written on disk.
     * @param tokenizerType A tag that represents the type of {@link src.java.tokenizer.Tokenizer} that was used
     *                      in the indexation process. This tags are located in {@link src.java.constants.Constants}.
     * @param corpusSize The size of the corpus
     * @param scoringSystem The tag corresponding to the scoring system used to create the index
     */
    void saveIndexToFile(String fileName, InvertedIndex index, String tokenizerType, int corpusSize, String scoringSystem);
}
