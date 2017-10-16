package src.java.query;

import java.io.File;
import java.util.List;

public interface QueryReader {
     List<Query> loadQueries(File queryFile);
}
