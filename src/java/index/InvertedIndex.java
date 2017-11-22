package src.java.index;

import java.util.*;

/**
 * A data structure that represents an invertedIndex that stores how many times a
 * term appears in each file along with the document id. Each invertedIndex entry points to a
 * list of Postings.
 *
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see Posting
 * */
public class InvertedIndex {
     private Map<String, Set<Posting>> index;

    /**
     * Constructs an empty invertedIndex.
     */
    public InvertedIndex(){
         this.index = new HashMap<>();
     }

    /**
     *
     * @return A {@link Set} of key terms
     */
    public Set<String> getTerms(){
        return index.keySet();
    }

    /**
     * Maps a term to a certain score for a document.
     * @param term The term where the score will be associated to
     * @param docID The id of the document
     * @param score The score of the document
     */
    public void addNormalizedScore(String term, int docID, double score){
        Set<Posting> entryList = index.get(term);
        if(entryList != null){
            entryList.add(new Posting(docID,score));
        }
        else{
            Set<Posting> newEntryList = new TreeSet<>(Comparator.comparingInt(Posting::getDocID));
            newEntryList.add(new Posting(docID,score));
            index.put(term,newEntryList);
        }
    }

    /**
     * Adds an occurrence of a token in a document with a certain ID to the index.
     * @param token A word/token, its occurrence will be stored in the index.
     * @param docID The ID of the document where the token appeared.
     *
     * */
    public void addTokenOccurrence(String token, int docID){
        Set<Posting> entryList = index.get(token);

        if(entryList != null){
            Posting entry = findEntry(entryList, docID);
            if(entry != null)
                entry.weight++;
            else
                entryList.add(new Posting(docID,1));
        }
        else{
            Set<Posting> newEntryList = new TreeSet<>();
            newEntryList.add(new Posting(docID,1));
            index.put(token,newEntryList);
        }
    }

    /**
     *
     * @param term the term to which the postings are associated
     * @return The {@link Set} of {@link Posting} objects associated with the term, or null in case the term
     * is not in the index
     */
    public Set<Posting> getPostings(String term){
         return index.get(term);
     }

    /**
     *
     * @param term A term to which the values will be mapped
     * @param postings A {@link Set} of {@link Posting} objects associated with the term
     */
    public void addTermAndPostings(String term, Set<Posting> postings){
         if(!index.containsKey(term))
            index.put(term, postings);
    }

    /**
     * Returns the size of the invertedIndex vocabulary which corresponds to the number of terms in the invertedIndex.
     * @return The size of the vocabulary that is indexed.
     */
    public int vocabularySize(){
        return index.size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String k : index.keySet()){
            sb.append(k+ ": " + index.get(k)+ "\n");
        }
        return sb.toString();
    }

    private Posting findEntry(Set<Posting> entryList, int docID){
        for(Posting entry : entryList){
            if(entry.getDocID() == docID)
                return entry;
        }
        return null;
    }


}
