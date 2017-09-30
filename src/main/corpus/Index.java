import java.util.*;

/**
 * A data structure that represents an index that stores how many times a
 * term appears in each file along with the document id.
 *
 * @author Pedro Matos
 * @author David Ferreira
 * @since 09-27-2017
 * */
public class Index {
     private Map<String, List<IndexEntry>> index;

    /**
     * Constructs an empty index.
     */
    public Index(){
         this.index = new HashMap<>();
     }

     /**
      * Adds an occurrence of a token in a document with a certain ID to the index.
      * @param token A word/token, its occurrence will be stored in the index.
      * @param docID The ID of the document where the token appeared.
      *
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
     * Returns a List which contains the 10 terms of a index, alphabetically ordered, with
     * the most occurrences in a certain document.
     * @param   docID The ID of the document
     * @return  A List with the top 10 terms that have the most occurrences in a document
     * @see List
     */
    public List<String> getTop10TermsOccurrences(int docID){
         List<String> termsInDoc = new ArrayList();
         for(String x: index.keySet()){
             List<IndexEntry> entry = index.get(x);
             for(int j=0; j<entry.size(); j++){
                if(entry.get(j).docID == docID) {
                    termsInDoc.add(x);
                    break;
                }
             }
         }
         Collections.sort(termsInDoc);
         return termsInDoc.subList(0,10);
    }

    /**
     * Returns a List with the 10 terms with the highest document frequency.
     * @return A List with the top 10 terms with higher document frequency.
     * @see List
     */
    public List<String> getTopFreqTerms(){
        String[] topTen = new String[10];
        Integer[] tempMax = new Integer[10];
        int min = Integer.MAX_VALUE;

        for(int n=0;n<10;n++){
            tempMax[n] = 0;
            topTen[n] = "";
        }

        for(String x: index.keySet()){
            for(int i=0; i<10; i++){
                if(min > tempMax[i])
                    min = tempMax[i];
            }
            for(int j=0; j<10; j++){
                if(tempMax[j] == min && tempMax[j] < index.get(x).size()){
                    tempMax[j] = index.get(x).size();
                    topTen[j] = x;
                    break;
                }
            }
            min=Integer.MAX_VALUE;
        }
        return Arrays.asList(topTen);
    }

    /**
     * Returns the size of the index vocabulary which corresponds to the number of terms in the index.
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
