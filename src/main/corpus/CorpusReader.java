public abstract class CorpusReader {
    private XMLReader reader;

    public CorpusReader(DocumentReader reader){
        this.reader = reader;
    }

    public abstract String processDocument();

}
