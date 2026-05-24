package carshop.service;

import carshop.dao.CarDao;
import carshop.entity.Car;

import java.math.BigDecimal;
import java.util.List;

/**
 * Выполняет бизнес-операции с автомобилями.
 */
public class CarService {
    private final CarDao carDao;

    public CarService(CarDao carDao) {
        this.carDao = carDao;
    }

    /**
     * Создаёт и сохраняет автомобиль.
     *
     * @param brand           марка автомобиля
     * @param model           модель автомобиля
     * @param manufactureYear год выпуска
     * @param horsePower      мощность в лошадиных силах
     * @param price           стоимость автомобиля
     * @return сохранённый автомобиль
     */
    public Car createCar(String brand, String model, Integer manufactureYear, Integer horsePower, BigDecimal price) {
        Car car = new Car(brand, model, manufactureYear, horsePower, price);

        Long numberValue = carDao.getNextInventoryNumberValue();
        String inventoryNumber = formatInventoryNumber(numberValue);

        car.setInventoryNumber(inventoryNumber);

        return carDao.save(car);
    }

    /**
     * Ищет автомобиль по идентификатору.
     *
     * @param id идентификатор автомобиля
     * @return найденный автомобиль или null, если автомобиль отсутствует
     */
    public Car findCarById(Long id) {
        return carDao.findById(id);
    }

    /**
     * Возвращает все автомобили.
     *
     * @return список автомобилей
     */
    public List<Car> findAllCars() {
        return carDao.findAll();
    }

    /**
     * Обновляет данные автомобиля.
     *
     * @param car автомобиль с обновлёнными данными
     * @return обновлённый автомобиль
     */
    public Car updateCar(Car car) {
        return carDao.update(car);
    }

    /**
     * Удаляет автомобиль по идентификатору.
     *
     * @param id идентификатор автомобиля
     */
    public void deleteCarById(Long id) {
        carDao.deleteById(id);
    }

    private String formatInventoryNumber(Long numberValue) {
        return String.format("CAR-%06d", numberValue);
    }
}
