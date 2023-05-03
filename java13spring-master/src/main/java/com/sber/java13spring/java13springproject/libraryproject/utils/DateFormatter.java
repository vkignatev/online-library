package com.sber.java13spring.java13springproject.libraryproject.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Утильный класс для работы с датами/строками.
 */
public class DateFormatter {
    private DateFormatter() {
    }
    
    //Задаем желаемый формат даты.
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    
    /***
     * Метод форматирующий поданную на вход строку в нужный формат даты и возвращающий дату.
     * @param dateToFormat - Строка, которую нужно преобразовать в дату.
     * @return - LocalDate: дата в нужном формате.
     */
    public static LocalDate formatStringToDate(final String dateToFormat) {
        return LocalDate.parse(dateToFormat, FORMATTER);
    }
}
