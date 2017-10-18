package src.java.index;

import src.java.tokenizer.Tokenizer;

import java.io.File;

public interface IndexReader {
    Tokenizer getTokenizer();
    Index parseToIndex(File indexFile);
}
