package carshop.dao;

import carshop.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Выполняет базовые CRUD-операции для сущностей Hibernate.
 *
 * @param <T> тип сущности
 */
public class GenericDao<T> {
    private final Class<T> entityClass;

    public GenericDao(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * Сохраняет сущность в базе данных.
     *
     * @param entity сущность для сохранения
     * @return сохранённая сущность
     */
    public T save(T entity) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            session.persist(entity);

            transaction.commit();

            return entity;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    /**
     * Ищет сущность по идентификатору.
     *
     * @param id идентификатор сущности
     * @return найденная сущность или null, если сущность отсутствует
     */
    public T findById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.find(entityClass, id);
        }
    }

    /**
     * Возвращает все сущности указанного типа из базы данных.
     *
     * @return список сущностей
     */
    public List<T> findAll() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String entity = entityClass.getSimpleName();
            String query = "from " + entity;

            return session.createQuery(query, entityClass).list();
        }
    }

    /**
     * Обновляет сущность в базе данных.
     *
     * @param entity сущность с обновлёнными данными
     * @return обновленная сущность
     */
    public T update(T entity) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            T mergedEntity = session.merge(entity);
            transaction.commit();

            return mergedEntity;
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }

    /**
     * Удаляет сущность по идентификатору.
     *
     * @param id идентификатор сущности
     */
    public void deleteById(Long id) {
        Transaction transaction = null;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            T entity = session.find(entityClass, id);

            if (entity != null) {
                session.remove(entity);
            }

            transaction.commit();
        } catch (RuntimeException e) {
            if (transaction != null) {
                transaction.rollback();
            }

            throw e;
        }
    }
}
