package src.java.index;

import src.java.normalizer.Vector;
import java.util.Map;
import java.util.Set;

public abstract class Index {
    protected Map<Integer, Vector> vectors;


    public Set<Integer> getIds() {
        return vectors.keySet();
    }

    public Vector getVector(int id){
        return vectors.get(id);
    }
}
