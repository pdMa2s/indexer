public class DirIteratorCorpusReader extends CorpusReader {
    private String dirName;
    private XMLReader;

    public DirIteratorCorpusReader(String dirName){
        XMLReader xmlreader = new XMLReader();
        super(xmlreader);
        this.dirName = dirName;
    }

    public DirIteratorCorpusReader(){
        XMLReader xmlreader = new XMLReader();
        super(xmlreader);
    }
    public String processDocument() {
        return null;
    }
}
