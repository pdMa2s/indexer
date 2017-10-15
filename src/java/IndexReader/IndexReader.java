package src.java.IndexReader;

import java.io.File;

public interface IndexReader {

        void open(File file);
        void parseAndIndex();
}
