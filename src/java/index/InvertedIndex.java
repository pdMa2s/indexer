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

    public Set<String> getTerms(){
        return index.keySet();
    }

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

    public Set<Posting> getPostingList(String term){
         return index.get(term);
     }

    public void addTermAndPostings(String token, Set<Posting> postings){
         if(!index.containsKey(token))
            index.put(token, postings);
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
