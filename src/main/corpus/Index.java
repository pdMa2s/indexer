import java.util.*;

public class Index {
     private Map<String, List<IndexEntry>> index;

     public Index(){
         this.index = new HashMap<>();
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
             List<IndexEntry> newEntryList = new ArrayList<>();
             newEntryList.add(new IndexEntry(docID,1));
             index.put(token,newEntryList);
         }
     }

    public List<String> getTop10Terms(int DocId){
         List<String> termsInDoc = new ArrayList();
         for(String x: index.keySet()){
             List<IndexEntry> entry = index.get(x);
             for(int j=0; j<entry.size(); j++){
                if(entry.get(j).docID == DocId) {
                    termsInDoc.add(x);
                    break;
                }
             }
         }
         Collections.sort(termsInDoc);
         return termsInDoc.subList(0,10);
    }

    public List<String> getTopFreqTerms(){
        String[] topTen = new String[10];
        Integer[] tempMax = new Integer[10];
        int count = 0, min = 99999;

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
            min=99999;
        }
        return Arrays.asList(topTen);
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
