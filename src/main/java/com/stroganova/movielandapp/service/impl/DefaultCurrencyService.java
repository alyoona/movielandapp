package com.stroganova.movielandapp.service.impl;

import com.stroganova.movielandapp.request.Currency;
import com.stroganova.movielandapp.nbu.ExchangeRateParser;
import com.stroganova.movielandapp.service.CurrencyService;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
public class DefaultCurrencyService implements CurrencyService {

    @NonNull ExchangeRateParser exchangeRateParser;

    @Override
    public double convert(double price, Currency currency) {
        return price / exchangeRateParser.getRate(currency, LocalDate.now());
    }
}
