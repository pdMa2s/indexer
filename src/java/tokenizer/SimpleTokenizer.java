package tokenizer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * An implementation of the interface {@link Tokenizer}, creates a collection of tokens based on a documents content.
 * Token filtration is made by removing all non-alphabetic characters, eliminating words with three characters or less and
 * set all the characters to lower case.
 * @author Pedro Matos - 73941
 * @author David Ferreira
 * @since 09-27-2017
 * @see Tokenizer
 */
public class SimpleTokenizer implements Tokenizer{

    /**
     * This implementation uses simple String manipulation to remove all non-alphabetic characters, eliminate words with three characters or less and
     * set all the characters to lower case.
     * @param docInfo A String which represents the contents of a file
     * @return A List of tokens with alphabetic characters, tokens with more than three characters and on lowercase
     */
    @Override
    public List<String> tokenize(String docInfo) {
        return Stream
                .of(docInfo)
                .map(w -> w.replaceAll("[^a-z\\s]+", "")).parallel()
                .map(w -> w.replaceAll("\\b[a-z]{1,2}\\b", "")).parallel()
                .map(w -> w.trim())
                .map(String::toLowerCase).parallel()
                .map(s -> s.split("\\s+")).flatMap(Arrays::stream).parallel()
                .collect(Collectors.toList());
    }
}
