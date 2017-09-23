public class Pair<U, V> {

    private U docsIDs;
    private V termFreq;

    public Pair(U docsIDs, V termFreq){
        this.docsIDs = docsIDs;
        this.termFreq = termFreq;
    }
}
