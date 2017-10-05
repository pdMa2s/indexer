package indexer;

/**
 * This class represents an entry on the index, it has representative purposes. This class stores the
 * the ID of a document where a certain term appears as well as the number of occurrences of the term
 * in that document.
 *
 * @author Pedro Matos
 * @author David Ferreira
 * @since 09-27-2017
 * @see Index
 */
public class Posting implements Comparable<Posting>{

    int docID;
    int termFreq;

    public Posting(int docID, int termFreq) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Posting)) return false;

        Posting that = (Posting) o;

        if (getDocID() != that.getDocID()) return false;
        return getTermFreq() == that.getTermFreq();
    }

    @Override
    public int hashCode() {
        int result = getDocID();
        result = 31 * result + getTermFreq();
        return result;
    }

    @Override
    public String toString() {
        return "{"+
                "docID=" + docID +
                ", termFreq=" + termFreq +
                '}';
    }

    @Override
    public int compareTo(Posting posting) {
        return this.docID - posting.docID;
    }
}