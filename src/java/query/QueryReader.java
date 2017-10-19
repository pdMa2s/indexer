package src.java.query;

import src.java.tokenizer.Tokenizer;

import java.io.File;
import java.util.List;

/**
 * A reader of a file that contains queries for the {@link src.java.index.Index}. Which query is represented
 * as an object of type {@link Query}, while reading queries, the reader must provide an id for the query
 * an it's content.
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 */
public interface QueryReader {
    /**
     * Reads the queries from a file an loads them to a {@link List}, with the respective id and content.
     * @param queryFile The file that contains the queries.
     * @param tokenizer A tokenizer to process the text from the query file.
     * @return A {@link List} of {@link Query} objects that represent the queries on the file.
     */
     List<Query> loadQueries(File queryFile, Tokenizer tokenizer);
}
