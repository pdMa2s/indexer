import java.util.*;

/**
 * A data structure that represents an index that stores how many times a
 * term appears in each file along with the document id
 *
 * @author Pedro Matos
 * @author David Ferreira
 * @since 09-27-2017
 * */
public class Index {
     private Map<String, List<IndexEntry>> index;

     public Index(){
         this.index = new HashMap<>();
     }

     /**
      * @param token A word/token, its occurrence will be stored in the index.
      * @param docID The ID of the document where the token appeared.
      * Adds an occurrence of a token in a document with a certain ID to the index.
      * */
     public void addTokenOcurrence(String token, int docID){
         List<IndexEntry> entryList = index.get(token);

         if(entryList != null){
             IndexEntry entry = findEntry(entryList, docID);
             if(entry != null)
                entry.termFreq++;
             else
                 entryList.add(new IndexEntry(docID,1));
         }
         else{
             List<IndexEntry> newEntryList = new ArrayList<>();
             newEntryList.add(new IndexEntry(docID,1));
             index.put(token,newEntryList);
         }
     }

    /**
     *
     * @param indexTomerge An instance of the Class @see Index that is going to be merged with this index.
     * Combines all the entries of two indexes.
     */
    public void mergeIndex(Index indexTomerge){
        for (String key : indexTomerge.index.keySet()){
            if(this.index.containsKey(key)){
                List<IndexEntry> temp = this.index.get(key);
                temp.addAll(indexTomerge.index.get(key));
                this.index.put(key,temp);
            }
            else{
                this.index.put(key, indexTomerge.index.get(key));
            }
        }
    }

    /**
     *
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

    private IndexEntry findEntry(List<IndexEntry> entryList, int docID){
        for(IndexEntry entry : entryList){
            if(entry.getDocID() == docID)
                return entry;
        }
        return null;
    }

}
