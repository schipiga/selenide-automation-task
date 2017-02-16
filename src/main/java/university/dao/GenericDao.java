package university.dao;

import java.util.List;

/**
 * GenericDao provides an interface for interaction with persistent objects.
 *
 * @param <T> the type of persistent element
 */
public interface GenericDao<T> {

    /**
     * Returns the list of objects from a data store.
     *
     * @return the list of objects from a data store
     */
    List<T> findAll();
}
