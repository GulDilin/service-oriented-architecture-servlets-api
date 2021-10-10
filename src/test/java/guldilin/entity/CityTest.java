package guldilin.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("City tests")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CityTest {
    private SessionFactory sessionFactory;

    @BeforeAll
    void setUp() {
        this.sessionFactory = SessionFactoryBuilder.getSessionFactory();
    }

    @Test
    void testConnect() {
        Session session = sessionFactory.openSession();
        session.createQuery("FROM city").list();
    }

    @Test
    void criteriaTest() {
        EntityManager em = sessionFactory.createEntityManager();

        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<City> cityCriteria = cb.createQuery(City.class);
        Root<City> cityRoot = cityCriteria.from(City.class);
        cityCriteria.select(cityRoot);
        cityCriteria.where(cb.equal(cityRoot.get("name"), "Puper"));
        em.createQuery(cityCriteria)
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    void save() {
        EntityManager em = sessionFactory.createEntityManager();
        em.getTransaction().begin();
        Coordinates coordinates = Coordinates.builder()
                .x(0L)
                .y(0)
                .build();
        em.persist(coordinates);
        em.flush();
        City city = City.builder()
                .climate(Climate.MONSOON)
                .coordinates(coordinates)
                .name("Puper")
                .build();
        em.persist(city);
        em.flush();
        em.getTransaction().commit();
        System.out.println(city.toString());
    }

    @Test
    void delete() {
        EntityManager em = sessionFactory.createEntityManager();

        em.getTransaction().begin();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<City> cityCriteria = cb.createQuery(City.class);
        Root<City> cityRoot = cityCriteria.from(City.class);
        cityCriteria.select(cityRoot);
        cityCriteria.where(cb.equal(cityRoot.get("name"), "Puper"));
        em.createQuery(cityCriteria)
                .getResultList()
                .forEach(em::remove);
        em.getTransaction().commit();
        assertEquals(em.createQuery(cityCriteria).getResultList().size(), 0);

    }
}
