package com.stroganova.movielandapp.nbu.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stroganova.movielandapp.request.Currency;
import com.stroganova.movielandapp.nbu.ExchangeRateParser;
import com.stroganova.movielandapp.nbu.impl.util.UrlGenerator;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

@Service
public class NbuExchangeRateParser implements ExchangeRateParser {

    private UrlGenerator urlGenerator = new UrlGenerator();

    @Override
    public Double getRate(Currency currency, LocalDate currentDate) {
        String path = urlGenerator.get(currency, currentDate);
        try {
            URL url = new URL(path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    if (inputLine.contains("window.exchangeRate")) {
                        String[] parsedInputLine = inputLine.split("'");
                        String jsonData = parsedInputLine[1];
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode rootNode = objectMapper.readTree(jsonData);
                        Double sourceRateValue = rootNode.path("data").findPath(currency.name()).asDouble();
                        Double units = rootNode.path("currencies_units").get(currency.name()).asDouble();
                        connection.disconnect();
                        return sourceRateValue / units;
                    }
                }
            }
            connection.disconnect();
        } catch (Exception e) {
            throw new RuntimeException("handle Exception", e);
        }
        return null;
    }
}
