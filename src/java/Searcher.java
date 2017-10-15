package src.java;

import src.java.IndexReader.IndexReader;
import src.java.IndexReader.SimpleIndexReader;

import java.io.File;

public class Searcher {
    public static void main(String[] args){
        IndexReader idr = new SimpleIndexReader();
        File ContentFile = new File(args[0]);
        idr.open(ContentFile);
        idr.parseAndIndex();
    }
}
