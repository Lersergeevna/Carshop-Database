package carshop.service;

import carshop.dao.ManagerDao;
import carshop.entity.Manager;

import java.util.List;

/**
 * Выполняет бизнес-операции с менеджерами.
 */
public class ManagerService {
    private final ManagerDao managerDao;

    public ManagerService(ManagerDao managerDao) {
        this.managerDao = managerDao;
    }

    /**
     * Создает и сохраняет менеджера.
     *
     * @param fullName полное имя менеджера
     * @param email    электронный адрес менеджера
     * @param phone    телефон менеджера
     * @param position должность менеджера
     * @return сохраненный менеджер
     */
    public Manager createManager(String fullName, String email, String phone, String position) {
        Manager manager = new Manager(fullName, email, phone, position);

        Long numberValue = managerDao.getNextEmployeeNumberValue();
        String employeeNumber = formatEmployeeNumber(numberValue);

        manager.setEmployeeNumber(employeeNumber);

        return managerDao.save(manager);
    }

    /**
     * Ищет менеджера по идентификатору.
     *
     * @param id идентификатор менеджера
     * @return найденный менеджер или null, если менеджер отсутствует
     */
    public Manager findManagerById(Long id) {
        return managerDao.findById(id);
    }

    /**
     * Возвращает всех менеджеров.
     *
     * @return список менеджеров
     */
    public List<Manager> findAllManagers() {
        return managerDao.findAll();
    }

    /**
     * Обновляет данные менеджера.
     *
     * @param manager менеджер с обновлёнными данными
     * @return обновлённый менеджер
     */
    public Manager updateManager(Manager manager) {
        return managerDao.update(manager);
    }

    /**
     * Удаляет менеджера по идентификатору.
     *
     * @param id идентификатор менеджера
     */
    public void deleteManagerById(Long id) {
        managerDao.deleteById(id);
    }

    private String formatEmployeeNumber(Long numberValue) {
        return String.format("EMP-%06d", numberValue);
    }
}