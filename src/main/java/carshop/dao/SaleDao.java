package carshop.dao;

import carshop.entity.Sale;
import carshop.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Выполняет операции доступа к данным для сущности Sale.
 */
public class SaleDao extends GenericDao<Sale> {
    public SaleDao() {
        super(Sale.class);
    }

    public Long getNextSaleNumberValue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number value = (Number) session
                    .createNativeQuery("select nextval('sale_number_sequence')")
                    .getSingleResult();

            return value.longValue();
        }
    }
}
