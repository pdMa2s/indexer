package src.java.index;

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
    int termOccurrences;
    double termTF;

    /**
     * Constructs a Posting object.
     * @param docID The ID of a document.
     * @param termOccurrences The frequency of a term/word.
     */
    public Posting(int docID, int termOccurrences) {
        this.docID = docID;
        this.termOccurrences = termOccurrences;
        this.termTF = 0;
    }

    public Posting(int docID, double termTF) {
        this.docID = docID;
        this.termTF = termTF;
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
    public int getTermOccurrences() {
        return termOccurrences;
    }

    public double getTermTF(){ return termTF; }
    /**
     *
     * @param docID The ID of a document.
     */
    public void setDocIDs(int docID) {
        this.docID = docID;
    }

    /**
     *
     * @param occurrences The frequency of a term in a certain document.
     */
    public void setTermFreq(int occurrences) {
        this.termOccurrences = occurrences;
    }

    public void setTF(double tf) {
        this.termTF = tf;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posting posting = (Posting) o;

        if (docID != posting.docID) return false;
        if (termOccurrences != posting.termOccurrences) return false;
        return Double.compare(posting.termTF, termTF) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = docID;
        result = 31 * result + termOccurrences;
        temp = Double.doubleToLongBits(termTF);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "{"+
                "docID=" + docID +
                ", termFreq=" + termOccurrences +
                '}';
    }

    @Override
    public int compareTo(Posting posting) {
        return this.docID - posting.docID;
    }
}