package com.stroganova.movielandapp.nbu.impl.util

import com.stroganova.movielandapp.request.Currency
import org.junit.Test

import java.time.LocalDate
import java.time.format.DateTimeFormatter


class UrlGeneratorTest  {
    private final static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    @Test
    void testGet() {
        UrlGenerator urlGenerator = new UrlGenerator()
        String date = LocalDate.of(2019, 9, 5).format(FORMATTER)
        Currency currency = Currency.USD
        def url = "https://bank.gov.ua/markets/exchangerate-chart?cn%5B%5D="+currency+"&startDate="+date+"&endDate="+date
        assert url == urlGenerator.get(currency, LocalDate.of(2019, 9, 5))

    }
}
