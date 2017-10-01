public abstract class IndexerBuilder {
    protected Indexer indexer;
    protected String directoryName;

    public IndexerBuilder(String dirName){
        this.directoryName = dirName;
    }
    public Indexer getIndexer() {
        return indexer;
    }

    public void createIndexer(){
        indexer = new Indexer();
    }
    public Indexer constructIndexer(){
        createIndexer();
        buildCorpusReader();
        buildTokenizer();
        return indexer;
    }
    public abstract void buildCorpusReader();
    public abstract void buildTokenizer();
}
