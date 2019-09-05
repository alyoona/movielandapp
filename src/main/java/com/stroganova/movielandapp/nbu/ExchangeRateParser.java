package com.stroganova.movielandapp.nbu;

import com.stroganova.movielandapp.request.Currency;

import java.time.LocalDate;

public interface ExchangeRateParser {

    Double getRate(Currency currency, LocalDate currentDate);
}
