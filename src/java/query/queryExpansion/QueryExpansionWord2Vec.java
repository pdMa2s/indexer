package src.java.query.queryExpansion;

import org.deeplearning4j.models.embeddings.loader.WordVectorSerializer;
import org.deeplearning4j.models.word2vec.Word2Vec;
import org.deeplearning4j.text.sentenceiterator.BasicLineIterator;
import org.deeplearning4j.text.sentenceiterator.SentenceIterator;
import org.deeplearning4j.text.tokenization.tokenizer.preprocessor.CommonPreprocessor;
import org.deeplearning4j.text.tokenization.tokenizerfactory.DefaultTokenizerFactory;
import org.deeplearning4j.text.tokenization.tokenizerfactory.TokenizerFactory;
import org.nd4j.linalg.exception.ND4JIllegalStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import static src.java.constants.Constants.WORD2VECMODEL;

/**
 * This finds the similarities between the words of the corpus and calculates how closely related the other words are
 * to one another
 */
public class QueryExpansionWord2Vec {
    private static Logger log = LoggerFactory.getLogger(QueryExpansionWord2Vec.class);
    private Word2Vec vec;

    /**
     * Constructs a QueryExpansionWord2Vec object
     * @param fullContentFile The file that contains the full content of the corpus
     * @throws FileNotFoundException Throw when the fullContentFile is not found
     */
    public QueryExpansionWord2Vec(String fullContentFile) throws FileNotFoundException {
        this.vec = loadModel();
        if(this.vec == null)
            createModel(fullContentFile);
    }

    public QueryExpansionWord2Vec(){}

    private Word2Vec loadModel(){
        try {
            return WordVectorSerializer.readWord2VecModel(WORD2VECMODEL);
        }catch (ND4JIllegalStateException ignored){ }
        return null;
    }
    public void createModel(String fullContentFile) throws FileNotFoundException {
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
        log.info("Closest Words:");
    }
    /**
     * @param word The reference word.
     * @return The 3 nearest words to the reference word.
     */
    public Collection<String> getSimilarWords(String word){
        return vec.wordsNearest(word, 3);
    }

    public void persistModel(String modelName){
        WordVectorSerializer.writeWord2VecModel(vec, modelName);
    }
}
