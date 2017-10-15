package src.java.queryProcessingUnits;

import src.java.indexer.Index;
import src.java.indexer.Posting;
import src.java.tokenizer.Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BooleanQueryProcessor implements QueryProcessor {
    private File contentFile;
    private BufferedReader reader = null;
    private Tokenizer tokenizer = null;
    private List<String> queryTokens = null;
    private String queryMethod = null;
    Index idx = null;

    public BooleanQueryProcessor(Tokenizer tokenizer, String queryMethod, Index idx){
        this.tokenizer = tokenizer;
        this.queryMethod = queryMethod;
        this.idx = idx;
    }

    public void open(File file){
        this.contentFile = file;
    }

    public void processQueries(){
        try {
            reader = new BufferedReader(new FileReader(contentFile));
            String text = null;
            while ((text = reader.readLine()) != null) {
                queryTokens = tokenizer.tokenize(text);
                processQuery(queryTokens);
            }
        }catch(FileNotFoundException e){} catch(IOException e){}
    }

    public void processQuery(List<String> tokens){
        if(queryMethod.equals("1")){
            Map<Integer, Integer> tempMap = new HashMap();
            for(String token : tokens){
                List<Posting> postings = idx.getTokenList(token);
                for(Posting pst : postings){
                    if(tempMap.get(pst.getDocID()) == null)
                        tempMap.put(pst.getDocID(), 1);
                    else
                        tempMap.put(pst.getDocID(), tempMap.get(pst.getDocID()) + 1);
                }
            }
            //Apenas falta retornar os Directorios por ordem de ocorrencia
        }else{
            Map<Integer, Integer> tempMap = new HashMap();
            for(String token : tokens){
                List<Posting> postings = idx.getTokenList(token);
                for(Posting pst : postings){
                    if(tempMap.get(pst.getDocID()) == null)
                        tempMap.put(pst.getDocID(), pst.getTermFreq());
                    else
                        tempMap.put(pst.getDocID(), tempMap.get(pst.getDocID()) + pst.getTermFreq());
                }
            }
            //Apenas falta retornar Diretorios ordenados por termFreq
        }
    }
}
