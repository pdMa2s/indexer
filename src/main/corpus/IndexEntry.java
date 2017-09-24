public class IndexEntry<U, V> {

    public U docsIDs;
    public V termFreq;

    public IndexEntry(U docsIDs, V termFreq){
        this.docsIDs = docsIDs;
        this.termFreq = termFreq;
    }

    public U getDocsID(){
        return docsIDs;
    }

    public V getTermFreq(){
        return termFreq;
    }

    public void setDocsIDs(U docID){
        this.docsIDs = docID;
    }

    public void setTermFreq(V tFreq){
        this.termFreq = tFreq;
    }
}
