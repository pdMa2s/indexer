package src.java.index;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class CSVIndexWriter implements IndexWriter{

    @Override
    public void saveIndexToFile(String fileName, Index index, String tokenizerType) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing index to file");
            System.exit(3);
        }
        writeHeader(writer, tokenizerType);
        writeTerms(writer, index);

    }
    private void writeHeader(PrintWriter writer, String tokenizerType){
        writer.println(tokenizerType);
    }
    private void writeTerms(PrintWriter writer, Index index){
        for(String term: index.getTerms()){
            writer.print(term);
            for(Posting post : index.getPostingList(term)){
                writer.print(","+post.getDocID() + ":" + post.getTermFreq());
            }
            writer.print("\n");
        }
        writer.close();
    }

}
