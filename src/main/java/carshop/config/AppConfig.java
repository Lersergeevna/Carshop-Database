package carshop.config;

import carshop.app.ConsoleApp;
import carshop.dao.*;
import carshop.service.*;

/**
 * Собирает зависимости приложения.
 */
public class AppConfig {

    public ConsoleApp createConsoleApp() {
        CarDao carDao = new CarDao();
        ClientDao clientDao = new ClientDao();
        ManagerDao managerDao = new ManagerDao();
        SaleDao saleDao = new SaleDao();
        CarPassportDao carPassportDao = new CarPassportDao();

        CarService carService = new CarService(carDao);
        ClientService clientService = new ClientService(clientDao);
        ManagerService managerService = new ManagerService(managerDao);
        SaleService saleService = new SaleService(saleDao, clientDao, managerDao, carDao);
        CarPassportService carPassportService = new CarPassportService(carDao, carPassportDao);

        return new ConsoleApp(
                carService,
                clientService,
                managerService,
                saleService,
                carPassportService
        );
    }
}