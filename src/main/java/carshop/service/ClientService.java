package carshop.service;

import carshop.dao.ClientDao;
import carshop.entity.Client;

import java.util.List;

/**
 * Выполняет бизнес-операции с клиентами.
 */
public class ClientService {
    private final ClientDao clientDao;

    public ClientService(ClientDao clientDao) {
        this.clientDao = clientDao;
    }

    /**
     * Создает и сохраняет клиента.
     *
     * @param fullName полное имя клиента
     * @param email    электронный адрес клиента
     * @param phone    телефон клиента
     * @return сохраненный клиент
     */
    public Client createClient(String fullName, String email, String phone) {
        Long numberValue = clientDao.getNextClientNumberValue();
        String clientNumber = formatClientNumber(numberValue);

        Client client = new Client(fullName, email, phone);
        client.setClientNumber(clientNumber);
        return clientDao.save(client);
    }

    private String formatClientNumber(Long numberValue) {
        return String.format("CL-%06d", numberValue);
    }

    /**
     * Ищет клиента по идентификатору.
     *
     * @param id идентификатор клиента
     * @return найденный клиент или null, если клиент отсутствует
     */
    public Client findClientById(Long id) {
        return clientDao.findById(id);
    }

    /**
     * Возвращает всех клиентов.
     *
     * @return список клиентов
     */
    public List<Client> findAllClients() {
        return clientDao.findAll();
    }

    /**
     * Обновляет данные клиента.
     *
     * @param client клиент с обновлёнными данными
     * @return обновлённый клиент
     */
    public Client updateClient(Client client) {
        return clientDao.update(client);
    }

    /**
     * Удаляет клиента по идентификатору.
     *
     * @param id идентификатор клиента
     */
    public void deleteClientById(Long id) {
        clientDao.deleteById(id);
    }
}
