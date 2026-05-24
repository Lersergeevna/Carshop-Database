package carshop.common;

import static carshop.common.MessagesPrefixes.*;

/**
 * Форматирует пользовательские сообщения,
 * добавляя стандартные префиксы ошибки, успеха
 * или информационного сообщения.
 */
public final class MessageFormatter {
    public static String formatError(String message) {
        return ERROR_PREFIX + message;
    }

    public static String formatSuccess(String message) {
        return SUCCESS_PREFIX + message;
    }

    public static String formatInfo(String message) {
        return INFO_PREFIX + message;
    }

    private MessageFormatter() {
    }
}
