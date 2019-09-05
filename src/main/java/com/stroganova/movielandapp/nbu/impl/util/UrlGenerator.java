package com.stroganova.movielandapp.nbu.impl.util;

import com.stroganova.movielandapp.request.Currency;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class UrlGenerator {
    private final static String HOST = "https://bank.gov.ua";
    private final static String ENDPOINT = "/markets/exchangerate-chart";
    private final static String CURRENCY_PARAM = "?cn%5B%5D=";
    private final static String START_DATE_PARAM = "&startDate=";
    private final static String END_DATE_PARAM = "&endDate=";
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public String get(Currency currency, LocalDate currentDate) {
        String formattedCurrentDate = currentDate.format(FORMATTER);
        return HOST + ENDPOINT
                + CURRENCY_PARAM + currency
                + START_DATE_PARAM + formattedCurrentDate
                + END_DATE_PARAM + formattedCurrentDate;
    }
}
