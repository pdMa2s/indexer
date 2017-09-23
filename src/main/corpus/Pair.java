public class Pair<U, V> {

    private U docsIDs;
    private V termFreq;

    public Pair(U docsIDs, V termFreq){
        this.docsIDs = docsIDs;
        this.termFreq = termFreq;
    }

    protected U getDocsID(){
        return docsIDs;
    }

    protected V getTermFreq(){
        return termFreq;
    }

    protected void setDocsIDs(U docID){
        this.docsIDs = docID;
    }

    protected void setTermFreq(V tFreq){
        this.termFreq = tFreq;
    }
}
