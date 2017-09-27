public class IndexEntry {

    int docID;
    int termFreq;

    public IndexEntry(int docID, int termFreq) {
        this.docID = docID;
        this.termFreq = termFreq;
    }

    public int getDocID() {
        return docID;
    }

    public int getTermFreq() {
        return termFreq;
    }

    public void setDocIDs(int docID) {
        this.docID = docID;
    }

    public void setTermFreq(int tFreq) {
        this.termFreq = tFreq;
    }


    @Override
    public String toString() {
        return "Entry{" +  docID + '\'' + "," + termFreq + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IndexEntry)) return false;

        IndexEntry that = (IndexEntry) o;

        if (getDocID() != that.getDocID()) return false;
        return getTermFreq() == that.getTermFreq();
    }

    @Override
    public int hashCode() {
        int result = getDocID();
        result = 31 * result + getTermFreq();
        return result;
    }
}