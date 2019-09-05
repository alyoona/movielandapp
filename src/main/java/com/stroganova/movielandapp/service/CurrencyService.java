package com.stroganova.movielandapp.service;

import com.stroganova.movielandapp.request.Currency;

public interface CurrencyService {

    double convert(double price, Currency currency);
}
