package carshop.dao;

import carshop.entity.Client;
import carshop.util.HibernateUtil;
import org.hibernate.Session;

/**
 * Выполняет операции доступа к данным для сущности Client.
 */
public class ClientDao extends GenericDao<Client> {
    public ClientDao() {
        super(Client.class);
    }

    /**
     * Возвращает следующее значение последовательности для номера клиента.
     *
     * @return следующее числовое значение клиентского номера
     */
    public Long getNextClientNumberValue() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Number value = (Number) session
                    .createNativeQuery("select nextval('client_number_sequence')")
                    .getSingleResult();

            return value.longValue();
        }
    }
}
