package carshop.app;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Читает и валидирует пользовательский ввод из консоли.
 */
public class InputHelper {
    private final Scanner scanner;

    public InputHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readCommand(String prompt) {
        while (true) {
            System.out.println(prompt);
            String value = scanner.nextLine().trim();

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Команда не может быть пустой.");
        }
    }

    public String readString(String prompt) {
        while (true) {
            System.out.println(prompt + " (q — отмена)");
            String value = scanner.nextLine().trim();

            if (value.equalsIgnoreCase("q")) {
                throw new OperationCancelledException();
            }

            if (!value.isEmpty()) {
                return value;
            }

            System.out.println("Значение не может быть пустым.");
        }
    }

    public Integer readInteger(String prompt) {
        while (true) {
            String value = readString(prompt);

            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    public Long readLong(String prompt) {
        while (true) {
            String value = readString(prompt);

            try {
                return Long.parseLong(value);
            } catch (NumberFormatException e) {
                System.out.println("Введите целое число.");
            }
        }
    }

    public BigDecimal readBigDecimal(String prompt) {
        while (true) {
            String value = readString(prompt);

            try {
                return new BigDecimal(value);
            } catch (NumberFormatException e) {
                System.out.println("Введите число. Например: 1500000 или 1500000.50");
            }
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            String value = readString(prompt);

            try {
                return LocalDate.parse(value);
            } catch (DateTimeParseException e) {
                System.out.println("Введите дату в формате YYYY-MM-DD.");
            }
        }
    }


}
