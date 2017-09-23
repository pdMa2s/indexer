import java.util.List;

public class SimpleTokenizer implements Tokenizer{

    @Override
    public String[] tokenize (String docInfo) {
        return docInfo.split("[^-\\w]+");
    }

    public SimpleTokenizer(){}
}
