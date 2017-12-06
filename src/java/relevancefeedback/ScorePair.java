package src.java.relevancefeedback;

public class ScorePair {
    int docId;
    int relevanceScore;

    public ScorePair(int docId, int relevanceScore) {
        this.docId = docId;
        this.relevanceScore = relevanceScore;
    }
}
