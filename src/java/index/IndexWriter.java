package src.java.index;

public interface IndexWriter {
    void saveIndexToFile(String fileName, Index index, String tokenizerType);
}
