import java.util.List;

public class SimpleTokenizer implements Tokenizer{

    @Override
    public String[] tokenize (String docInfo) {
        return docInfo.split("[^-a-zA-Z0-9']");
    }

    public SimpleTokenizer(){}
}
