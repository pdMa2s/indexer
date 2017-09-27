public interface CorpusReader {
    String processDocument();
    boolean hasDocument();
    int getDocumentID();
}
