package src.java.index;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * This implementation of {@link IndexWriter} writes an {@link InvertedIndex} object on disk, with a format similar
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
    public void saveIndexToFile(String fileName, InvertedIndex index, String tokenizerType, int corpusSize) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing invertedIndex to file");
            System.exit(3);
        }
        writeHeader(writer, tokenizerType, corpusSize);
        writeTerms(writer, index);

    }

    private void writeHeader(PrintWriter writer, String tokenizerType, int corpusSize){
        writer.println(tokenizerType + ":" + corpusSize);
    }
    private void writeTerms(PrintWriter writer, InvertedIndex index){
        for(String term: index.getTerms()){
            writer.print(term);
            for(Posting post : index.getPostings(term)){
                if(post.getNormalizedWeight() == 0)
                    writer.print(","+post.getDocID() + ":" + post.getTermOccurrences());
                else
                    writer.print(","+post.getDocID() + ":" + post.getNormalizedWeight());
            }
            writer.print("\n");
        }
        writer.close();
    }

}
