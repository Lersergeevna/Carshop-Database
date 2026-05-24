package carshop.dao;

import carshop.entity.Car;
import carshop.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Выполняет операции доступа к данным для сущности Car.
 */
public class CarDao extends GenericDao<Car> {
    public CarDao() {
        super(Car.class);
    }

    public Long getNextInventoryNumberValue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number value = (Number) session
                    .createNativeQuery("select nextval('car_inventory_sequence')")
                    .getSingleResult();

            return value.longValue();
        }
    }
}
