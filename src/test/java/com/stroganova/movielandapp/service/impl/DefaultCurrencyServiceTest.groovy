package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.nbu.ExchangeRateParser
import com.stroganova.movielandapp.request.Currency
import org.junit.Test

import java.time.LocalDate


import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when


class DefaultCurrencyServiceTest {

    @Test
    void testConvert() {
        def exchangeRateParser = mock(ExchangeRateParser.class)
        def currencyService = new DefaultCurrencyService(exchangeRateParser)
        when(exchangeRateParser.getRate(Currency.USD, LocalDate.now())).thenReturn(Double.valueOf(25.52D))
        assert 90D / 25.52D == currencyService.convert(90D, Currency.USD)
    }
}
