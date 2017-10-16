package src.java.index;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVIndexReader implements IndexReader {

    private Index index;
    private BufferedReader reader;
    private List<Posting> postingPerTerm;

    public CSVIndexReader(){
        index = new Index();
        postingPerTerm = new ArrayList<>();
        reader = null;

    }


    @Override
    public Index parseToIndex(File indexFile){
        try{
            reader = new BufferedReader(new FileReader(indexFile));
            String text;
            while ((text = reader.readLine()) != null) {
                String[] postings = text.split(",");
                postingPerTerm = parsePostingsPerTerm(Arrays.copyOfRange(postings, 0,postings.length ));
                index.addTokenAndPostings(postings[0], postingPerTerm);
            }
        }catch(FileNotFoundException e){
            System.err.println("index file not found!");
            System.exit(3);
        } catch(IOException e){
            System.err.println("ERROR: Reading index file");
            System.exit(2);
        }
        return index;
    }

    private List<Posting> parsePostingsPerTerm(String[] postings){
        List<Posting> ppt= new ArrayList<>();

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
