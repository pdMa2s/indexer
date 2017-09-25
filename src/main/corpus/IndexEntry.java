public class IndexEntry {

    String docsIDs;
    int termFreq;

    public IndexEntry(String docsIDs, int termFreq) {
        this.docsIDs = docsIDs;
        this.termFreq = termFreq;
    }

    public String getDocID() {
        return docsIDs;
    }

    public int getTermFreq() {
        return termFreq;
    }

    public void setDocIDs(String docID) {
        this.docsIDs = docID;
    }

    public void setTermFreq(int tFreq) {
        this.termFreq = tFreq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexEntry)) return false;

        IndexEntry that = (IndexEntry) o;

        if (getTermFreq() != that.getTermFreq()) return false;
        return docsIDs != null ? docsIDs.equals(that.docsIDs) : that.docsIDs == null;
    }

    @Override
    public int hashCode() {
        int result = docsIDs != null ? docsIDs.hashCode() : 0;
        result = 31 * result + getTermFreq();
        return result;
    }

    @Override
    public String toString() {
        return "Entry{" +  docsIDs + '\'' + "," + termFreq + '}';
    }
}