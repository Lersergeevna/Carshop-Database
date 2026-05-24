package carshop.app;

import carshop.entity.*;
import carshop.service.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

/**
 * Запускает консольное приложение автосалона.
 */
public class ConsoleApp {
    private final CarService carService;
    private final ClientService clientService;
    private final ManagerService managerService;
    private final SaleService saleService;
    private final CarPassportService carPassportService;
    private final Scanner scanner = new Scanner(System.in);
    private final InputHelper inputHelper = new InputHelper(scanner);

    public ConsoleApp(
            CarService carService,
            ClientService clientService,
            ManagerService managerService,
            SaleService saleService,
            CarPassportService carPassportService) {
        this.carService = carService;
        this.clientService = clientService;
        this.managerService = managerService;
        this.saleService = saleService;
        this.carPassportService = carPassportService;
    }

    public void run() {
        boolean running = true;

        while (running) {
            showMenu();
            String command = inputHelper.readCommand("Выберите команду:");
            running = handleCommand(command);
        }
    }

    private void showMenu() {
        System.out.println("1 — Создать автомобиль");
        System.out.println("2 — Создать паспорт для автомобиля");
        System.out.println("3 — Показать все автомобили");
        System.out.println("4 — Создать клиента");
        System.out.println("5 — Показать всех клиентов");
        System.out.println("6 — Создать менеджера");
        System.out.println("7 — Показать всех менеджеров");
        System.out.println("8 — Создать продажу");
        System.out.println("9 — Показать все продажи");
        System.out.println("0 или q — Выход");
    }

    private boolean handleCommand(String command) {
        try {
            switch (command.toLowerCase()) {
                case "1":
                    createCar();
                    return true;
                case "2":
                    createPassportForCar();
                    return true;
                case "3":
                    showAllCars();
                    return true;
                case "4":
                    createClient();
                    return true;
                case "5":
                    showAllClients();
                    return true;
                case "6":
                    createManager();
                    return true;
                case "7":
                    showAllManagers();
                    return true;
                case "8":
                    createSale();
                    return true;
                case "9":
                    showAllSales();
                    return true;
                case "0":
                case "q":
                    System.out.println("Приложение завершено.");
                    return false;
                default:
                    System.out.println("Неверный ввод.");
                    return true;
            }
        } catch (OperationCancelledException e) {
            System.out.println(e.getMessage());
            return true;
        } catch (RuntimeException e) {
            System.out.println("Ошибка: " + e.getMessage());
            return true;
        }
    }

    private void createCar() {
        String brand = inputHelper.readString("Введите марку:");
        String model = inputHelper.readString("Введите модель:");
        Integer manufactureYear = inputHelper.readInteger("Введите год выпуска:");
        Integer horsePower = inputHelper.readInteger("Введите мощность:");

        BigDecimal price = inputHelper.readBigDecimal("Введите стоимость:");

        Car createdCar = carService.createCar(brand, model, manufactureYear, horsePower, price);

        System.out.println("Создан автомобиль: " + createdCar);
    }

    private void showAllCars() {
        printList(carService.findAllCars(), "Автомобили не найдены.");
    }

    private void createPassportForCar() {
        System.out.println("Список автомобилей:");
        showAllCars();

        Long carId = inputHelper.readLong("Выберите id автомобиля:");

        System.out.println("Введите данные паспорта:");

        String vin = inputHelper.readString("Введите VIN:");
        String color = inputHelper.readString("Введите цвет:");
        String engineNumber = inputHelper.readString("Введите номер двигателя:");

        CarPassport passport = carPassportService.createPassportForCar(carId, vin, color, engineNumber);

        System.out.println("Создан паспорт автомобиля: " + passport);
    }

    private void createClient() {
        String fullName = inputHelper.readString("Введите полное имя:");
        String email = inputHelper.readString("Введите email клиента:");
        String phone = inputHelper.readString("Введите телефон клиента:");

        Client createdClient = clientService.createClient(fullName, email, phone);

        System.out.println("Создан клиент: " + createdClient);
    }

    private void showAllClients() {
        printList(clientService.findAllClients(), "Клиенты не найдены.");
    }

    private void createManager() {
        String fullName = inputHelper.readString("Введите полное имя менеджера:");
        String email = inputHelper.readString("Введите email менеджера:");
        String phone = inputHelper.readString("Введите телефон менеджера:");
        String position = inputHelper.readString("Введите должность менеджера:");

        Manager createdManager = managerService.createManager(fullName, email, phone, position);

        System.out.println("Создан менеджер: " + createdManager);
    }

    private void showAllManagers() {
        printList(managerService.findAllManagers(), "Менеджеры не найдены.");
    }

    private void createSale() {
        System.out.println("Список клиентов:");
        showAllClients();

        Long clientId = inputHelper.readLong("Выберите ID клиента:");

        System.out.println("Список менеджеров:");
        showAllManagers();

        Long managerId = inputHelper.readLong("Выберите ID менеджера:");

        System.out.println("Список автомобилей:");
        showAllCars();

        Long carId = inputHelper.readLong("Выберите ID автомобиля:");

        LocalDate saleDate = inputHelper.readDate("Введите дату продажи в формате YYYY-MM-DD:");

        Sale createdSale = saleService.createSale(clientId, managerId, carId, saleDate);

        System.out.println("Создана продажа: " + createdSale);
    }

    private void showAllSales() {
        printList(saleService.findAllSales(), "Продажи не найдены.");
    }

    private <T> void printList(List<T> items, String emptyMessage) {
        if (items.isEmpty()) {
            System.out.println(emptyMessage);
        } else {
            items.forEach(System.out::println);
        }
    }
}