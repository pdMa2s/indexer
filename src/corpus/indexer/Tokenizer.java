package indexer;

import java.util.List;

/**
 * An Object the implements the indexer.Tokenizer interface has to breakdown a String into several tokens using a certain set of
 * rules which the Object is free to chose.
 * @author Pedro Matos
 * @author David Ferreira
 * @see String
 */
public interface Tokenizer {
    /**
     * This functions uses a String to extract a List os tokens/words.
     * @param docInfo A String which represents the contents of a file
     * @return A List of tokens extracted from @param docInfo
     * @see List
     */
    List<String> tokenize(String docInfo);
}
