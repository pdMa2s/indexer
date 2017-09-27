import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ComplexTokenizer implements Tokenizer{

    @Override
    public List<String> tokenize (String docInfo) {
        return new ArrayList<>(Arrays.asList(docInfo.split("[^-\\w']+")))  ;
    }

}