package src.java.index;

import src.java.normalizer.Vector;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * The base class for an index that maps an id to a {@link Vector}
 */
public abstract class Index {
    protected Map<Integer, Vector> vectors;

    /**
     * Constructs an {@link Index} object.
     */
    public Index(){
        vectors = new TreeMap<>();
    }

    /**
     *
     * @return The vectors on the index
     */
    public Set<Integer> getIds() {
        return vectors.keySet();
    }

    /**
     *
     * @param id the id to which a vector is mapped to
     * @return the {@link Vector} associated with the id
     */
    public Vector getVector(int id){
        return vectors.get(id);
    }
}
