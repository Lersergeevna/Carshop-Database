package carshop;

import carshop.common.MessageFormatter;
import carshop.common.Messages;
import carshop.config.AppConfig;
import carshop.util.HibernateUtil;
import carshop.util.LiquibaseUtil;

public class Main {
    public static void main(String[] args) {
        try {
            LiquibaseUtil.update();

            HibernateUtil.getSessionFactory();
            System.out.println(MessageFormatter.formatSuccess(Messages.HIBERNATE_STARTED_SUCCESS));

            new AppConfig().createConsoleApp().run();
        } catch (RuntimeException ex) {
            System.out.println(MessageFormatter.formatError(Messages.UNKNOWN_ERROR));
            ex.printStackTrace();
        } finally {
            HibernateUtil.shutdown();
        }
    }
}