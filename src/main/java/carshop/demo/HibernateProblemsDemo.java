package carshop.demo;

import carshop.entity.Client;
import carshop.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;

public class HibernateProblemsDemo {

    public void reproduceLazyInitializationException(Long clientId) {
        Client client;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            client = session.find(Client.class, clientId);
        }

        System.out.println("Клиент загружен: " + client);

        System.out.println("Пробуем получить продажи клиента после закрытия session...");
        System.out.println(client.getSales().size());
    }

    public void fixLazyInitializationExceptionWithJoinFetch(Long clientId) {
        Client client;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            client = session.createQuery("""
                        select c
                        from Client c
                        left join fetch c.sales
                        where c.id = :clientId
                        """, Client.class)
                    .setParameter("clientId", clientId)
                    .getSingleResult();
        }

        System.out.println("Клиент загружен: " + client);

        System.out.println("Пробуем получить продажи клиента после закрытия session...");
        System.out.println("Количество продаж клиента: " + client.getSales().size());
    }

    public void fixLazyInitializationExceptionInsideSession(Long clientId) {
        Client client;

        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            client = session.find(Client.class, clientId);

            System.out.println("Инициализируем sales внутри открытой session...");
            client.getSales().size();
        }

        System.out.println("Клиент загружен: " + client);

        System.out.println("Пробуем получить продажи клиента после закрытия session...");
        System.out.println("Количество продаж клиента: " + client.getSales().size());
    }

    public void reproduceNPlusOneProblem() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Client> clients = session.createQuery("""
                        select c
                        from Client c
                        """, Client.class)
                    .getResultList();

            System.out.println("Количество клиентов: " + clients.size());

            for (Client client : clients) {
                System.out.println(
                        "Клиент id=" + client.getId()
                                + ", продаж: " + client.getSales().size()
                );
            }
        }
    }

    public void fixNPlusOneWithJoinFetch() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Client> clients = session.createQuery("""
                        select distinct c
                        from Client c
                        left join fetch c.sales
                        """, Client.class)
                    .getResultList();

            System.out.println("Количество клиентов: " + clients.size());

            for (Client client : clients) {
                System.out.println(
                        "Клиент id=" + client.getId()
                                + ", продаж: " + client.getSales().size()
                );
            }
        }
    }

    public void fixNPlusOneWithBatchSize() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            List<Client> clients = session.createQuery("""
                        select c
                        from Client c
                        """, Client.class)
                    .getResultList();

            System.out.println("Количество клиентов: " + clients.size());

            for (Client client : clients) {
                System.out.println(
                        "Клиент id=" + client.getId()
                                + ", продаж: " + client.getSales().size()
                );
            }
        }
    }

    public void runAll(Long clientId) {
        runDemoExpectingError(
                "1. Воспроизведение LazyInitializationException",
                () -> reproduceLazyInitializationException(clientId)
        );

        runDemo(
                "2. Решение LazyInitializationException через JOIN FETCH",
                () -> fixLazyInitializationExceptionWithJoinFetch(clientId)
        );

        runDemo(
                "3. Решение LazyInitializationException через инициализацию внутри Session",
                () -> fixLazyInitializationExceptionInsideSession(clientId)
        );

        runDemo(
                "4. Воспроизведение N+1",
                this::reproduceNPlusOneProblem
        );

        runDemo(
                "5. Решение N+1 через JOIN FETCH",
                this::fixNPlusOneWithJoinFetch
        );

        runDemo(
                "6. Решение N+1 через @BatchSize",
                this::fixNPlusOneWithBatchSize
        );
    }

    private void runDemo(String title, Runnable demo) {
        System.out.println();
        System.out.println("========================================");
        System.out.println(title);
        System.out.println("========================================");

        demo.run();
    }

    private void runDemoExpectingError(String title, Runnable demo) {
        System.out.println();
        System.out.println("========================================");
        System.out.println(title);
        System.out.println("========================================");

        try {
            demo.run();
        } catch (RuntimeException e) {
            System.out.println("Поймали ожидаемую ошибку:");
            System.out.println(e.getClass().getSimpleName() + ": " + e.getMessage());
        }
    }
}