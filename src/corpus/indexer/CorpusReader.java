package indexer;

/**
 * A reader of a collection of documents(corpus). The user of this interface knows if there is still objects in
 * a directory to be processed. After a document is processed the user as access to a @see String that represents
 * the content of a file. The ID of the file is also provided.
 *
 * @author Pedro Matos
 * @since 09-27-2017
 * */
public interface CorpusReader {
    String processDocument();
    boolean hasDocument();
    int getDocumentID();
}
