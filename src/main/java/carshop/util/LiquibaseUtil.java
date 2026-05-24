package carshop.util;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Запускает миграции Liquibase перед стартом Hibernate.
 */
public final class LiquibaseUtil {
    private static final String CHANGELOG_FILE = "db/changelog/db.changelog-master.xml";

    private static final String URL = "jdbc:postgresql://localhost:5433/car_shop";
    private static final String USERNAME = "carshop_user";
    private static final String PASSWORD = "carshop_password";

    private LiquibaseUtil() {
    }

    public static void update() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));

            Liquibase liquibase = new Liquibase(
                    CHANGELOG_FILE,
                    new ClassLoaderResourceAccessor(),
                    database
            );

            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при выполнении миграций Liquibase", e);
        }
    }
}