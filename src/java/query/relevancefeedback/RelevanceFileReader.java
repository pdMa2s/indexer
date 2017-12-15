package src.java.query.relevancefeedback;

/**
 * Reads the relevance feedback file
 * @author Pedro Matos
 * @author David Ferreira
 * @since 12-15-2017
 */
public interface RelevanceFileReader {
    /**
     * This functions parses the relevance scores files and returns a {@link RelevanceIndex}
     * @param filePath The relevance score file
     * @return A {@link RelevanceIndex} object
     */
    RelevanceIndex parseRelevanceFile(String filePath);
}
