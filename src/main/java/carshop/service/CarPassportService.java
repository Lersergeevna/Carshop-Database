package carshop.service;

import carshop.dao.CarDao;
import carshop.dao.CarPassportDao;
import carshop.entity.Car;
import carshop.entity.CarPassport;

import static carshop.common.Messages.ENTITY_NOT_FOUND;

/**
 * Выполняет бизнес-операции с техническими паспортами автомобилей.
 */
public class CarPassportService {
    private final CarDao carDao;
    private final CarPassportDao carPassportDao;

    public CarPassportService(CarDao carDao, CarPassportDao carPassportDao) {
        this.carDao = carDao;
        this.carPassportDao = carPassportDao;
    }

    /**
     * Создаёт паспорт для автомобиля по id.
     *
     * @param carId        идентификатор автомобиля
     * @param vin          VIN автомобиля
     * @param color        цвет автомобиля
     * @param engineNumber номер двигателя
     * @return паспорт автомобиля
     */
    public CarPassport createPassportForCar(Long carId, String vin, String color, String engineNumber) {
        Car car = carDao.findById(carId);
        if (car == null) {
            throw new IllegalArgumentException(ENTITY_NOT_FOUND);
        }

        CarPassport passport = new CarPassport(vin, color, engineNumber);
        passport.setCarId(car.getId());

        return carPassportDao.save(passport);
    }

    /**
     * Ищет паспорт автомобиля по идентификатору.
     *
     * @param passportId идентификатор паспорта
     * @return найденный паспорт или null, если паспорт отсутствует
     */
    public CarPassport findPassportById(Long passportId) {
        return carPassportDao.findById(passportId);
    }

    /**
     * Удаляет паспорт автомобиля по идентификатору.
     *
     * @param id идентификатор паспорта
     */
    public void deletePassportById(Long id) {
        CarPassport passport = carPassportDao.findById(id);
        if (passport == null) {
            throw new IllegalArgumentException(ENTITY_NOT_FOUND);
        }
        carPassportDao.deleteById(id);
    }
}
