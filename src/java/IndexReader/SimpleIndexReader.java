package src.java.IndexReader;

import src.java.indexer.Index;

import java.io.File;

public class SimpleIndexReader implements IndexReader {

    private File contentFile;
    private Index idx;
    public SimpleIndexReader(){}

    public void open(File file){
        this.contentFile = file;
        this.idx = new Index();
    }

    public void parseAndIndex(){
        
    }
}
