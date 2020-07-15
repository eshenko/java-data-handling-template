package com.epam.izh.rd.online.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleRegExpService implements RegExpService {

    /**
     * Метод должен читать файл sensitive_data.txt (из директории resources) и маскировать в нем конфиденциальную информацию.
     * Номер счета должен содержать только первые 4 и последние 4 цифры (1234 **** **** 5678). Метод должен содержать регулярное
     * выражение для поиска счета.
     *
     * @return обработанный текст
     */
    @Override
    public String maskSensitiveData() {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(Objects.requireNonNull(getClass()
                .getClassLoader().getResource("sensitive_data.txt")).getFile()))) {
            result.append(reader.readLine());

            Pattern pattern = Pattern.compile("\\d{4}\\s(\\d{4}\\s\\d{4})\\s\\d{4}");
            Matcher matcher = pattern.matcher(result);
            while (matcher.find()) {
                result.replace(matcher.start(1), matcher.end(1), "**** ****");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    /**
     * Метод должен считыввать файл sensitive_data.txt (из директории resources) и заменять плейсхолдер ${payment_amount} и ${balance} на заданные числа. Метод должен
     * содержать регулярное выражение для поиска плейсхолдеров
     *
     * @return обработанный текст
     */
    @Override
    public String replacePlaceholders(double paymentAmount, double balance) {
        String result = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(Objects.requireNonNull(getClass()
                .getClassLoader().getResource("sensitive_data.txt")).getFile()))) {
            result = reader.readLine();
            result = result.replaceAll("\\$\\{payment_amount}", String.valueOf((int)paymentAmount));
            result = result.replaceAll("\\$\\{balance}", String.valueOf((int)balance));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
