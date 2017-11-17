package src.java.index;

/**
 * This class represents an entry on the invertedIndex, it has representative purposes. This class stores the
 * the ID of a document where a certain term appears as well as the number of occurrences of the term
 * in that document.
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see InvertedIndex
 */
public class Posting implements Comparable<Posting>{

    int docID;
    double weight;


    public Posting(int docID, double weight) {
        this.docID = docID;
        this.weight = weight;
    }

    /**
     *
     * @return The ID of this posting document.
     */
    public int getDocID() {
        return docID;
    }

    public double getWeight(){ return weight; }
    /**
     *
     * @param docID The ID of a document.
     */
    public void setDocIDs(int docID) {
        this.docID = docID;
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Posting posting = (Posting) o;

        if (docID != posting.docID) return false;
        return Double.compare(posting.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = docID;
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "{"+
                "docID=" + docID +
                ", termWeight=" + weight +
                '}';
    }

    @Override
    public int compareTo(Posting posting) {
        return this.docID - posting.docID;
    }
}