package src.java.query.relevancefeedback;

public interface RelevanceFileReader {
    RelevanceIndex parseRelevanceFile(String filePath);
}
