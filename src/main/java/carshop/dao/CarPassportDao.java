package carshop.dao;

import carshop.entity.CarPassport;

/**
 * Выполняет операции доступа к данным для сущности CarPassport.
 */
public class CarPassportDao extends GenericDao<CarPassport> {
    public CarPassportDao() {
        super(CarPassport.class);
    }
}