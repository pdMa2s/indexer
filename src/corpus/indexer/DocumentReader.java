package indexer;

import java.io.File;
public interface DocumentReader {
    void open(File file);
    String parse();
    int getDocumentID();
}