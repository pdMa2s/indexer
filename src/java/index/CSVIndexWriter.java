package src.java.index;

import src.java.normalizer.Vector;
import src.java.query.DocumentIndex;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

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
    private String delimiter = ":";
    /**
     * {@inheritDoc}
     */
    @Override
    public void saveIndexToFile(String fileName, InvertedIndex index, String tokenizerType, int corpusSize,
                                String scoringSystem) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(fileName, "UTF-8");
        } catch (UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing invertedIndex to file");
            System.exit(3);
        }
        writeHeader(writer, tokenizerType, corpusSize, scoringSystem);
        writeTerms(writer, index);

    }

    public void saveDocumentIndexToFile(String documentIndexFile, DocumentIndex docIndex){
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(documentIndexFile, "UTF-8");
        } catch(UnsupportedEncodingException | FileNotFoundException e) {
            System.err.println("ERROR: Writing documentIndex to file");
            System.exit(4);
        }
        for(Integer docID: docIndex.getDocIds()){
            writer.print(docID);
            Vector tempVector = docIndex.getTermsVector(docID);
            for(String term : tempVector.getTerms()){
                writer.print(","+term + delimiter + tempVector.getScore(term));
            }
            writer.print("\n");
        }
        writer.close();
    }

    private void writeHeader(PrintWriter writer, String tokenizerType, int corpusSize, String scoringSystem){
        writer.println(scoringSystem+ delimiter +tokenizerType + delimiter + corpusSize);
    }

    private void writeTerms(PrintWriter writer, InvertedIndex index){
        for(String term: index.getTerms()){
            writer.print(term);
            for(Posting post : index.getPostings(term)){
                writer.print(","+post.getDocID() + delimiter + post.getWeight());
            }
            writer.print("\n");
        }
        writer.close();
    }

}
