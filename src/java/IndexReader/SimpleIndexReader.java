package src.java.IndexReader;

import src.java.indexer.Index;
import src.java.indexer.Posting;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleIndexReader implements IndexReader {

    private File contentFile;
    private Index idx;
    BufferedReader reader = null;
    private String actualTerm = null;
    private List<Posting> postingPerTerm= new ArrayList();

    public SimpleIndexReader(Index index){ this.idx = index;}

    public void open(File file){
        this.contentFile = file;
    }

    public void parseAndIndex(){
        try{
            reader = new BufferedReader(new FileReader(contentFile));
            String text = null;
            while ((text = reader.readLine()) != null) {
                postingPerTerm = parsePostingsPerTerm(text);
                idx.addTokenAndPostings(actualTerm, postingPerTerm);
            }
        }catch(FileNotFoundException e){} catch(IOException e){}
    }

    public List<Posting> parsePostingsPerTerm(String text){
        List<Posting> ppt= new ArrayList();
        String[] postings = text.split(",");
        actualTerm = postings[0];
        for(int i=1; i<postings.length; i++){
            String[] post = postings[i].split(":");
            if(post.length == 2) {
                Posting tempPosting = new Posting(Integer.parseInt(post[0]), Integer.parseInt(post[1]));
                ppt.add(tempPosting);
            }
        }
        return ppt;
    }
}
