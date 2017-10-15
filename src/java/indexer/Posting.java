package src.java.indexer;

/**
 * This class represents an entry on the index, it has representative purposes. This class stores the
 * the ID of a document where a certain term appears as well as the number of occurrences of the term
 * in that document.
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see Index
 */
public class Posting implements Comparable<Posting>{

    int docID;
    int termFreq;

    /**
     * Constructs a Posting object.
     * @param docID The ID of a document.
     * @param termFreq The frequency of a term/word.
     */
    public Posting(int docID, int termFreq) {
        this.docID = docID;
        this.termFreq = termFreq;
    }

    /**
     *
     * @return The ID of this posting document.
     */
    public int getDocID() {
        return docID;
    }

    /**
     *
     * @return The frequency of the term/word.
     */
    public int getTermFreq() {
        return termFreq;
    }

    /**
     *
     * @param docID The ID of a document.
     */
    public void setDocIDs(int docID) {
        this.docID = docID;
    }

    /**
     *
     * @param termFreq The frequency of a term in a certain document.
     */
    public void setTermFreq(int termFreq) {
        this.termFreq = termFreq;
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