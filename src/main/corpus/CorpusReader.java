public interface CorpusReader {
    String processDocument();
    boolean hasDocument();
    String getDocumentID();
}
