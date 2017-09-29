import java.util.*;

public class Index {
     private Map<String, List<IndexEntry>> index;

     public Index(){
         this.index = new LinkedHashMap<>();
     }

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
             List<IndexEntry> newEntryList = new LinkedList<>();
             newEntryList.add(new IndexEntry(docID,1));
             index.put(token,newEntryList);
         }
     }

    public void mergeIndex(Index indexTomerge){
        for (String x : indexTomerge.index.keySet()){
            if(this.index.containsKey(x)){
                List<IndexEntry> temp = this.index.get(x);
                temp.addAll(indexTomerge.index.get(x));
                this.index.put(x,temp);
            }
            else{
                this.index.put(x, indexTomerge.index.get(x));
            }
        }
    }

    public int vocabularySize(){
        return index.size();
    }

     private IndexEntry findEntry(List<IndexEntry> entryList, int docID){
         for(IndexEntry entry : entryList){
             if(entry.getDocID() == docID)
                 return entry;
         }
         return null;
     }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(String k : index.keySet()){
            sb.append(k+ ": " + index.get(k)+ "\n");
        }
        return sb.toString();
    }
}
