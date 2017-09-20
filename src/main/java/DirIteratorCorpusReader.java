public class DirIteratorCorpusReader extends CorpusReader {
    private String dirName;

    public DirIteratorCorpusReader(DocumentReader reader, String dirName){
        super(reader);
        this.dirName = dirName;
    }
    public String processDocument() {
        return null;
    }
}
