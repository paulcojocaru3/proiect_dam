package telecom_suport.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional
public class EntityRepositoryJPA<T, ID>
        implements IRepository<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    private final Class<T> entityClass;

    public EntityRepositoryJPA(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T save(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(
                entityManager.find(entityClass, id)
        );
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery(
                "SELECT e FROM " +
                        entityManager.getMetamodel()
                                .entity(entityClass)
                                .getName() +
                        " e",
                entityClass
        ).getResultList();
    }

    @Override
    public void deleteById(ID id) {
        T entity = entityManager.find(entityClass, id);

        if (entity != null) {
            entityManager.remove(entity);
        }
    }
}