package src.java.corpus;

import src.java.corpus.documentreader.DocumentReader;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class that iterates files in a directory, returning their content.
 * The files are processed in alphabetic order.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 *
 */
public class DirIteratorCorpusReader implements CorpusReader {
    private List<File> dirFiles;
    private DocumentReader reader;
    private int fileIndex;

    /**
     * Constructs DirIteratorCorpusReader that loads a file list form a certain directory.
     * The files are sorted by alphabetic order.
     * @param dirName The name of the directory where that corpus is stored.
     * @param reader An implementation of a {@link DocumentReader} to parse the files.
     */
    public DirIteratorCorpusReader(String dirName,DocumentReader reader){
        this.reader = reader;
        File corpusDirectory = new File(dirName);
        checkIfFileIsDirectory(corpusDirectory);
        dirFiles = Arrays.asList(corpusDirectory.listFiles());
        Collections.sort(dirFiles);
        fileIndex = 0;
    }
    private void checkIfFileIsDirectory(File dir){
        if(!dir.exists() || !dir.isDirectory()){
            throw new IllegalArgumentException("The corpus location that was provided is not a directory");
        }
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public boolean hasDocument(){
        return !(fileIndex == dirFiles.size());
    }

    /**
     *
     * {@inheritDoc}
     */
    @Override
    public int getDocumentID() {
        return reader.getDocumentID();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int corpusSize() {
        return dirFiles.size();
    }

    /**
     * {@inheritDoc}
     * */
    @Override
    public String processDocument() {
        if(!hasDocument())
            return null;
        reader.open(dirFiles.get(fileIndex));
        fileIndex++;
        return reader.parse();
    }


}

