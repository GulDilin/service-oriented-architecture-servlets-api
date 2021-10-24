package guldilin.controller;

import com.google.gson.Gson;
import guldilin.dto.EntityListDTO;
import guldilin.entity.AbstractEntity;
import guldilin.errors.FilterTypeNotFound;
import guldilin.errors.FilterTypeNotSupported;
import guldilin.repository.interfaces.CrudRepository;
import guldilin.utils.FilterAction;
import guldilin.utils.FilterActionParser;
import guldilin.utils.FilterableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.Session;

import javax.persistence.EntityNotFoundException;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class CrudController<T extends AbstractEntity> {
    private CrudRepository<T> repository;
    private Class<T> tClass;
    private Gson gson;
    private Supplier<List<FilterableField>> getFields;

    void doGetById(Long id, HttpServletResponse response) throws IOException {
        response.getWriter().write(gson.toJson(
                repository.findById(id)
                        .orElseThrow(EntityNotFoundException::new)
                        .mapToDTO()));
    }

    private Predicate parseFilterAction(FilterAction action, CriteriaBuilder cb, Root root) throws FilterTypeNotFound {
        FilterableField field = action.getFilterableField();
        String fieldName = field.getName();
        System.out.println("Filter field " + fieldName + " by value " + action.getValue() + " mode " + action.getFilterType());
        switch (action.getFilterType()) {
            case EQUALS:
                return cb.equal(root.get(fieldName), field.getParser().apply(action.getValue()));
            case IS_NULL:
                return cb.isNull(root.get(fieldName));
            case CONTAINS:
                return cb.like(root.get(fieldName), "%" + field.getParser().apply(action.getValue()) + "%");
            case LESS_THAN:
                return cb.lessThan(root.get(fieldName), (Comparable) field.getTClass().cast(field.getParser().apply(action.getValue())) );
            case GREATER_THAN:
                return cb.greaterThan(root.get(fieldName), (Comparable) field.getTClass().cast(field.getParser().apply(action.getValue())) );
            case LESS_THAN_OR_EQUALS:
                return cb.lessThanOrEqualTo(root.get(fieldName), (Comparable) field.getTClass().cast(field.getParser().apply(action.getValue())) );
            case GREATER_THAN_OR_EQUALS:
                return cb.greaterThanOrEqualTo(root.get(fieldName), (Comparable) field.getTClass().cast(field.getParser().apply(action.getValue())) );
        }
        throw new FilterTypeNotFound();
    }

    void applyOrders(String[] orderings, CriteriaBuilder cb, CriteriaQuery criteria, Root root) {
        HashMap<String, Function<Expression<?>, Order>> orderingMap = new HashMap<>();
        Arrays.stream(orderings)
                .map(String::trim)
                .map(e -> !e.startsWith("-") && !e.startsWith("+") ? "+" + e : e)
                .forEach(e -> orderingMap.put(e.substring(1), e.startsWith("-") ? cb::desc : cb::asc));
        List<Order> orders = orderingMap
                .entrySet()
                .stream()
                .map(e -> e.getValue().apply(root.get(e.getKey())))
                .collect(Collectors.toList());
        criteria.orderBy(orders);
    }

    void applyFilters(HttpServletRequest request, CriteriaBuilder cb, CriteriaQuery criteria, Root root)
            throws FilterTypeNotFound, FilterTypeNotSupported {
        List<FilterableField> fields = getFields.get()
                .stream()
                .filter(e -> request.getParameter(e.getName()) != null)
                .collect(Collectors.toList());
        List<Predicate> predicates = new ArrayList<>();
        for (FilterableField field : fields) {
            FilterAction action = FilterActionParser.parse(request.getParameter(field.getName()), field);
            predicates.add(parseFilterAction(action, cb, root));
        }
        criteria.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
    }

    void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, FilterTypeNotFound, FilterTypeNotSupported {
        Optional<Long> id = Optional.ofNullable((Long) request.getAttribute("id"));
        if (id.isPresent()) {
            this.doGetById(id.get(), response);
            return;
        }

        Integer limit = Optional.ofNullable(request.getParameter("limit")).map(Integer::parseInt).orElse(10);
        Integer offset = Optional.ofNullable(request.getParameter("offset")).map(Integer::parseInt).orElse(0);
        String[] orderings = request.getParameterValues("sorting");

        Session session = repository.openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<T> criteria = cb.createQuery(tClass);
        Root<T> root = criteria.from(tClass);
        criteria.select(root);


        if (orderings != null) applyOrders(orderings, cb, criteria, root);
        applyFilters(request, cb, criteria, root);

        Query query = session.createQuery(criteria);
        query.setMaxResults(limit);
        query.setFirstResult(offset);
        List<T> entries = query.getResultList();

        entries.forEach(System.out::println);
        EntityListDTO listDTO = new EntityListDTO();
        listDTO.setResults(entries.stream().map(T::mapToDTO).collect(Collectors.toList()));
        listDTO.setTotal((long) entries.size());

        response.getWriter().write(gson.toJson(listDTO));
        session.close();
    }
}
