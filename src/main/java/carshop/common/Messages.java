package carshop.common;

/**
 * Хранит пользовательские сообщения консольного приложения.
 * Не содержит логики вывода и не должен создаваться как объект.
 */
public final class Messages {
    public static final String HIBERNATE_STARTED_SUCCESS = "Hibernate успешно запущен";
    public static final String HIBERNATE_START_ERROR = "Ошибка запуска Hibernate";
    public static final String HIBERNATE_SHUTDOWN_SUCCESS = "Hibernate успешно остановлен";

    public static final String INVALID_INPUT = "Неверный ввод";
    public static final String OPERATION_CANCELLED = "Операция отменена";
    public static final String UNKNOWN_ERROR = "Неизвестная ошибка";

    public static final String CREATED_SUCCESS = "Создание прошло успешно";
    public static final String UPDATED_SUCCESS = "Обновление прошло успешно";
    public static final String DELETED_SUCCESS = "Удаление прошло успешно";

    public static final String ENTITY_NOT_FOUND = "Сущность не найдена.";
    public static final String ENTITY_SAVE_ERROR = "Сущность не создана.";
    public static final String ENTITY_DELETE_ERROR = "Сущность не удалена.";

    private Messages() {
    }
}
