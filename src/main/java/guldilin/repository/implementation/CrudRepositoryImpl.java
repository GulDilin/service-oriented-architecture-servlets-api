package guldilin.repository.implementation;

import guldilin.entity.SessionFactoryBuilder;
import guldilin.repository.interfaces.CrudRepository;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

public class CrudRepositoryImpl<T> implements CrudRepository<T> {
    private final SessionFactory sessionFactory;

    public CrudRepositoryImpl() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Override
    public List<T> findByCriteria(CriteriaQuery<T> criteriaQuery) {
        EntityManager em = sessionFactory.createEntityManager();
        return em.createQuery(criteriaQuery).getResultList();
    }

    @Override
    public Optional<T> findById(Long id) {
        EntityManager em = sessionFactory.createEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        return Optional.empty();
    }

    @Override
    public T update(T coordinates) {
        return null;
    }

    @Override
    public void save(T coordinates) {

    }

    @Override
    public void deleteById(Long id) {

    }
}
