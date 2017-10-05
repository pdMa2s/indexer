package indexer;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DirIteratorCorpusReader implements CorpusReader {
    private List<File> dirFiles;
    private DocumentReader reader;
    private int fileIndex;

    public DirIteratorCorpusReader(String dirName,DocumentReader reader){
        this.reader = reader;
        dirFiles = Arrays.asList(new File(dirName).listFiles());
        Collections.sort(dirFiles);
        fileIndex = 0;

    }

    @Override
    public boolean hasDocument(){
        return !(fileIndex == dirFiles.size());
    }

    @Override
    public int getDocumentID() {
        return reader.getDocumentID();
    }

    @Override
    public String processDocument() {
        if(!hasDocument())
            return null;
        reader.open(dirFiles.get(fileIndex));
        fileIndex++;
        return reader.parse();
    }


}

