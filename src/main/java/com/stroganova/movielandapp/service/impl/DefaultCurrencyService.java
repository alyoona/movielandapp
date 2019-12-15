package com.stroganova.movielandapp.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stroganova.movielandapp.request.Currency;
import com.stroganova.movielandapp.service.CurrencyService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DefaultCurrencyService implements CurrencyService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private Map<Currency, Double> rateCache = new ConcurrentHashMap<>();

    @Override
    public double convert(double price, Currency currency) {
        return price / rateCache.get(currency);
    }

    @PostConstruct
    @Scheduled(cron = "* 01 17 * * *", zone = "Europe/Kiev")
    private void getRates() {
        String url = getNbuUrl();
        String responseHtml;
        try {
            responseHtml = restTemplate.getForObject(url, String.class);
        } catch (ResourceAccessException ex) {
            responseHtml = restTemplate.getForObject(url, String.class);
        }

        String[] lines = responseHtml.split("\n");
        for (String line : lines) {
            if (line.contains("window.exchangeRate")) {
                String[] parsedLines = line.split("'");
                String jsonData = parsedLines[1];
                try {
                    JsonNode rootNode = objectMapper.readTree(jsonData);
                    for (Currency currency : Currency.values()) {
                        Double sourceRateValue = rootNode.path("data").findPath(currency.getName()).asDouble();
                        Double units = rootNode.path("currencies_units").get(currency.getName()).asDouble();
                        rateCache.put(currency, sourceRateValue / units);
                    }
                    break;
                } catch (IOException e) {
                    throw new RuntimeException("error read tree jsonData", e);
                }
            }
        }
    }

    private String getNbuUrl() {
        String formattedCurrentDate = LocalDate.now().format(formatter);
        return "https://bank.gov.ua/markets/exchangerate-chart"
                + "?cn%5B%5D=" + Currency.USD
                + "&cn%5B%5D=" + Currency.EUR
                + "&startDate=" + formattedCurrentDate
                + "&endDate=" + formattedCurrentDate;
    }


}
