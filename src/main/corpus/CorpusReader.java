public abstract class CorpusReader {
    private DocumentReader reader;

    public CorpusReader(DocumentReader reader){
        this.reader = reader;
    }

    public abstract String processDocument();

}
