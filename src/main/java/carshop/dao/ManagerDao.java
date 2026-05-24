package carshop.dao;

import carshop.entity.Manager;
import carshop.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Выполняет операции доступа к данным для сущности Manager.
 */
public class ManagerDao extends GenericDao<Manager> {
    public ManagerDao() {
        super(Manager.class);
    }

    public Long getNextEmployeeNumberValue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number value = (Number) session
                    .createNativeQuery("select nextval('employee_number_sequence')")
                    .getSingleResult();

            return value.longValue();
        }
    }
}