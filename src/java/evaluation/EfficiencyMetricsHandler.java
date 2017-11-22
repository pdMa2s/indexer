package src.java.evaluation;

import src.java.query.Query;
import java.util.List;
import java.util.Map;

/**
 * A handler for the results of the evaluation and efficiency metrics.
 */
public interface EfficiencyMetricsHandler {
    /**
     * The implementation of this function receives as parameters {@link Map} where the keys are the
     * name of the measures with their mapped values along with a {@link List} of {@link Query} objects with their
     * respective results. This handles the results as it pleases, they can be print to the command line or written
     * into a file.
     * @param results A {@link Map} that contains the results for different measures
     * @param queries A {@link List} of {@link Query} objects in their respective evaluation and efficiency metrics results
     */
    void handleEfficiencyResults(Map<String, Double> results, List<Query> queries);
}
