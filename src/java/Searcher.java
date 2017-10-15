package src.java;

import src.java.IndexReader.IndexReader;
import src.java.IndexReader.SimpleIndexReader;
import src.java.indexer.Index;

import java.io.File;

public class Searcher {
    public static void main(String[] args){
        Index index = new Index();
        IndexReader idr = new SimpleIndexReader(index);
        File ContentFile = new File(args[0]);
        idr.open(ContentFile);
        idr.parseAndIndex();
    }
}
