package src.java.index;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * This implementation of {@link IndexWriter} writes an {@link Index} object on disk, with a format similar
 * to CSV.<br>
 * Example: term, documentId:DocumentFrequency,documentId:DocumentFrequency, ...
 * @author Pedro Matos
 * @author David Ferreira
 * @since 10-16-2017
 * @see <a href="https://pt.wikipedia.org/wiki/Comma-separated_values">CSV</a>
 */
public class CSVIndexWriter implements IndexWriter{

    /**
     * {@inheritDoc}
     */
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
