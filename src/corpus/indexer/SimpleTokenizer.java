package indexer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SimpleTokenizer implements Tokenizer{

    @Override
    public List<String> tokenize(String docInfo) {
        return Stream
                .of(docInfo)
                .map(w -> w.replaceAll("[^a-z\\s]+", ""))
                .map(w -> w.replaceAll("\\b[a-z]{1,2}\\b", ""))
                .map(w -> w.trim())
                .map(String::toLowerCase)
                .map(s -> s.split("\\s+")).flatMap(Arrays::stream)
                .collect(Collectors.toList());
    }
}
