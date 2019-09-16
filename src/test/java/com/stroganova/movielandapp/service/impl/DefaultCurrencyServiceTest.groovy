package com.stroganova.movielandapp.service.impl

import com.stroganova.movielandapp.request.Currency
import org.junit.Before
import org.junit.Test
import org.springframework.util.ReflectionUtils

import java.lang.reflect.Field

class DefaultCurrencyServiceTest {

    private Map<Currency, Double> rateCache = new HashMap<>()
    def currencyService = new DefaultCurrencyService()

    @Before
    void before() {
        rateCache.put(Currency.USD, 25D)
        rateCache.put(Currency.EUR, 29D)
        Field field = DefaultCurrencyService.class.getDeclaredField("rateCache")
        field.setAccessible(true)
        ReflectionUtils.setField(field, currencyService, rateCache)
        field.setAccessible(false)
    }

    @Test
    void testConvert() {
        assert 90D / 25D == currencyService.convert(90D, Currency.USD)
        assert 90D / 29D == currencyService.convert(90D, Currency.EUR)
    }
}
