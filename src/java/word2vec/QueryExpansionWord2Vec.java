package src.java.word2vec;



import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

public class QueryExpansionWord2Vec {
    private static Logger log = LoggerFactory.getLogger(QueryExpansionWord2Vec.class);
    private Word2Vec vec;

    public QueryExpansionWord2Vec(String fullContentFile) throws IOException {

        File file = new File(fullContentFile);
        String filePath = file.getAbsolutePath();

        log.info("Building model....");
        // Strip white space before and after for each line
        SentenceIterator iter = new BasicLineIterator(filePath);
        // Split on white spaces in the line to get words
        TokenizerFactory t = new DefaultTokenizerFactory();
        t.setTokenPreProcessor(new CommonPreprocessor());

        log.info("Building model....");
        this.vec = new Word2Vec.Builder()
                .minWordFrequency(5)
                .iterations(1)
                .layerSize(100)
                .seed(42)
                .windowSize(5)
                .iterate(iter)
                .tokenizerFactory(t)
                .build();

        log.info("Fitting Word2Vec model....");
        vec.fit();

        log.info("Writing word vectors to text file....");
        //10 palavras mais perto
        log.info("Closest Words:");
        //Collection<String> lst = vec.wordsNearest("potenti", 10);
        //System.out.println(vec.wordsNearest("potenti", 10));
        //System.out.println(vec.wordsNearest("continu", 10));
        //System.out.println(vec.wordsNearest("exact", 10));
    }

    public Collection<String> getSimilarWords(String word){
        return vec.wordsNearest(word, 3);
    }
}
