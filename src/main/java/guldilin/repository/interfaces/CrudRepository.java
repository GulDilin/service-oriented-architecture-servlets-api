package guldilin.repository.interfaces;

import guldilin.entity.Coordinates;

import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T> {
    List<T> findByCriteria(CriteriaQuery<T> criteriaQuery);
    Optional<T> findById(Long id);
    T update(T coordinates);
    void save(T coordinates);
    void deleteById(Long id);
}
