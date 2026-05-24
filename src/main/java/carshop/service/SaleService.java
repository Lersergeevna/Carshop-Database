package carshop.service;

import carshop.common.Messages;
import carshop.dao.CarDao;
import carshop.dao.ClientDao;
import carshop.dao.ManagerDao;
import carshop.dao.SaleDao;
import carshop.entity.Car;
import carshop.entity.Client;
import carshop.entity.Manager;
import carshop.entity.Sale;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

/**
 * Выполняет бизнес-операции с продажами автомобилей.
 */
public class SaleService {
    private final SaleDao saleDao;
    private final ClientDao clientDao;
    private final ManagerDao managerDao;
    private final CarDao carDao;

    public SaleService(SaleDao saleDao, ClientDao clientDao, ManagerDao managerDao, CarDao carDao) {
        this.saleDao = saleDao;
        this.clientDao = clientDao;
        this.managerDao = managerDao;
        this.carDao = carDao;
    }

    /**
     * Создаёт продажу автомобиля.
     *
     * @param clientId  идентификатор клиента
     * @param managerId идентификатор менеджера
     * @param carId     идентификатор автомобиля
     * @param saleDate  дата продажи
     * @return сохранённая продажа
     */
    public Sale createSale(Long clientId, Long managerId, Long carId, LocalDate saleDate) {
        Client client = clientDao.findById(clientId);
        if (client == null) {
            throw new IllegalArgumentException(Messages.ENTITY_NOT_FOUND);
        }

        Manager manager = managerDao.findById(managerId);
        if (manager == null) {
            throw new IllegalArgumentException(Messages.ENTITY_NOT_FOUND);
        }

        Car car = carDao.findById(carId);
        if (car == null) {
            throw new IllegalArgumentException(Messages.ENTITY_NOT_FOUND);
        }

        Sale sale = new Sale(saleDate, calculateFinalPrice(car, client));

        Long numberValue = saleDao.getNextSaleNumberValue();
        String saleNumber = formatSaleNumber(numberValue);

        sale.setSaleNumber(saleNumber);
        sale.setClient(client);
        sale.setManager(manager);
        sale.setCar(car);

        return saleDao.save(sale);
    }

    private String formatSaleNumber(Long numberValue) {
        return String.format("SL-%06d", numberValue);
    }

    private BigDecimal calculateFinalPrice(Car car, Client client) {
        BigDecimal price = car.getPrice();
        BigDecimal discountPercentage = client.getDiscountPercentage();
        BigDecimal hundred = BigDecimal.valueOf(100);

        BigDecimal discountAmount = price
                .multiply(discountPercentage)
                .divide(hundred);

        return price.subtract(discountAmount)
                .setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Ищет продажу по идентификатору.
     *
     * @param id идентификатор продажи
     * @return найденная продажа или null, если продажа отсутствует
     */
    public Sale findSaleById(Long id) {
        return saleDao.findById(id);
    }

    /**
     * Возвращает все продажи.
     *
     * @return список продаж
     */
    public List<Sale> findAllSales() {
        return saleDao.findAll();
    }

    /**
     * Удаляет продажу по идентификатору.
     *
     * @param id идентификатор продажи
     */
    public void deleteSaleById(Long id) {
        saleDao.deleteById(id);
    }
}