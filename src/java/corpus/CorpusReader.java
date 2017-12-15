package src.java.corpus;

/**
 * A reader of a collection of documents(corpus). The user of this interface knows if there is still objects in
 * a directory to be processed. After a document is processed the user as access to a {@link String} that represents
 * the content of a file. The ID of the file is also provided.
 * The documents are meant to be processed in a iterative way.
 *
 * @author Pedro Matos - 73941
 * @since 09-27-2017
 * */
public interface CorpusReader {
    /**
     * The purpose of this function is to extract the relevant content of a document into a {@link String}
     * @return A {@link String} with the document's content.
     */
    String processDocument();

    /**
     * Checks is there is still document to be processed in a certain directory.
     * @return false if there are no document to be processed, if else returns true
     */
    boolean hasDocument();

    /**
     * To each document should be assign an ID number after it's been processed.
     * @return The ID of the document that was processed.
     */
    int getDocumentID();

    /**
     *
     * @return The number of documents in the corpus
     */
    int corpusSize();
}
