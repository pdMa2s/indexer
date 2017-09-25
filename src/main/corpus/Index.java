import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Index {
     private Map<String, List<IndexEntry>> index;

     public Index(){
         this.index = new HashMap<>();
     }

     public void addTokenOcurrence(String token, String docID){
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


     private IndexEntry findEntry(List<IndexEntry> entryList, String docID){
         for(IndexEntry entry : entryList){
             if(entry.getDocID().equals(docID))
                 return entry;
         }
         return null;
     }

    @Override
    public String toString() {
        return "Index{"+ index + '}';
    }
}
