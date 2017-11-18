package src.java.evaluation;

import src.java.query.Query;

import java.util.List;
import java.util.Map;

public interface EfficiencyMetricsWriter {
    void saveEfficiencyResults(Map<String, Double> results, List<Query> queries);
}
