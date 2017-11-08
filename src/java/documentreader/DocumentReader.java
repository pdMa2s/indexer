package src.java.documentreader;

import java.io.File;

/**
 * A reader for the documents of a corpus, an implementation of this class has to be able to provide information
 * that is processing.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 *
 */
public interface DocumentReader {
    /**
     * Loads a file to be processed.
     * @param file The file to be processed.
     */
    void open(File file);

    /**
     * Strips the relevant content of a file and returns it.
     * @return A {@link String} with content of the file.
     */
    String parse();

    /**
     * Each file is represented by an identifier this function returns it in the form of an int.
     * @return The ID of the file that is being processed.
     */
    int getDocumentID();
}
