package src.java.queryProcessingUnits;

import java.io.File;

public interface QueryProcessor {
    void open(File file);
    void processQueries();
}
