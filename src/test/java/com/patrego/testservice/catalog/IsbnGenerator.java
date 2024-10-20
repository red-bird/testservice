package com.patrego.testservice.catalog;

import java.util.concurrent.atomic.AtomicInteger;

public class IsbnGenerator {

    private static AtomicInteger isbnCounter = new AtomicInteger(0);

    public static String generateIsbn13() {
        // Префикс 978
        String prefix = "978";

        // Генерация уникальной части, добавляя нули для соответствия длине 9 цифр
        String uniquePart = String.format("%09d", isbnCounter.getAndIncrement());

        // Составляем ISBN без контрольной цифры
        String isbnWithoutCheckDigit = prefix + uniquePart;

        // Вычисляем контрольную цифру
        int checkDigit = calculateCheckDigit(isbnWithoutCheckDigit);

        // Возвращаем полный ISBN
        return isbnWithoutCheckDigit + checkDigit;
    }

    private static int calculateCheckDigit(String isbn) {
        int sum = 0;
        for (int i = 0; i < isbn.length(); i++) {
            int digit = Character.getNumericValue(isbn.charAt(i));
            if (i % 2 == 0) {
                sum += digit; // Четные позиции
            } else {
                sum += digit * 3; // Нечетные позиции
            }
        }
        // Вычисляем контрольную цифру
        int remainder = sum % 10;
        return remainder == 0 ? 0 : 10 - remainder;
    }
}
