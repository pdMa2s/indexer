package src.java.index;

/**
 * An entity that writes an {@link Index} on disk with a format depending on the implementation.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public interface IndexWriter {
    /**
     * The implementation of this function should write in the first line of the file an header
     * that only contains a tag that identifies the {@link src.java.tokenizer.Tokenizer} that was used
     * to create the @param index.
     * After that, the @param index itself should be written.
     * @param fileName The of the file where the index will be written.
     * @param index The {@link Index} object to be written on disk.
     * @param tokenizerType A tag that represents the type of {@link src.java.tokenizer.Tokenizer} that was used
     *                      in the indexation process. This tags are located in {@link src.java.constants.Constants}.
     */
    void saveIndexToFile(String fileName, Index index, String tokenizerType);
}
