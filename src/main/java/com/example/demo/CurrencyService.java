package com.example.demo;

import org.springframework.stereotype.Service;
import org.json.*;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CurrencyService {
    public Map<String, Double> GetAverageExchangeRate(String currencyName, String date) throws IOException, CurrencyNotFoundException {
        try {
            JSONArray objArray = GetJSONArray(
                    "http://api.nbp.pl/api/exchangerates/rates/A/%s/%s/?format=json", currencyName, date, 0);

            Map<String, Double> currency = new HashMap<>();
            currency.put(objArray.getJSONObject(0).getString("effectiveDate"),
                    objArray.getJSONObject(0).getDouble("mid"));

            return currency;
        } catch (FileNotFoundException e) {
            throw new CurrencyNotFoundException();
        }
    }

    public Map<String, Double> GetMinMaxAverageValue(String currencyName, int numberOfDays) throws IOException, CurrencyNotFoundException {
        try {
            JSONArray objArray = GetJSONArray(
                    "http://api.nbp.pl/api/exchangerates/rates/A/%s/last/%d/", currencyName, null, numberOfDays);

            Map<String, Double> currencyValues = new HashMap<>();
            for (int x = 0; x < objArray.length(); x++) {
                currencyValues.put(objArray.getJSONObject(x).getString("effectiveDate"),
                        objArray.getJSONObject(x).getDouble("mid"));
            }

            Map<String, Double> minMax = new HashMap<>();
            minMax.put("min", Collections.min(currencyValues.values()));
            minMax.put("max", Collections.max(currencyValues.values()));
            return minMax;
        } catch (FileNotFoundException e) {
            throw new CurrencyNotFoundException();
        }
    }

    public Map<String, Double> GetMajorDiffBuyAskRate(String currencyName, int numberOfDays) throws IOException, CurrencyNotFoundException {
        try {
            JSONArray objArray = GetJSONArray(
                    "http://api.nbp.pl/api/exchangerates/rates/C/%s/last/%d/", currencyName, null, numberOfDays);

            Map<String, Double> currencyValues = new HashMap<>();
            for (int x = 0; x < objArray.length(); x++) {
                double bid = objArray.getJSONObject(x).getDouble("bid");
                double ask = objArray.getJSONObject(x).getDouble("ask");
                currencyValues.put(objArray.getJSONObject(x).getString("effectiveDate"),
                        BigDecimal.valueOf(ask-bid)
                                .setScale(4, RoundingMode.HALF_UP)
                                .doubleValue());
            }
            Map<String, Double> bidask = new HashMap<>();
            bidask.put("min", Collections.min(currencyValues.values()));
            bidask.put("max", Collections.max(currencyValues.values()));
            return bidask;
        } catch (FileNotFoundException e) {
            throw new CurrencyNotFoundException();
        }
    }

    private JSONArray GetJSONArray(String api_url, String currencyName, String date, int numberOfDays) throws IOException {
        URL url = new URL(String.format(api_url, currencyName, Objects.requireNonNullElse(date, numberOfDays)));
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );
        String inputLine;
        StringBuilder response = new StringBuilder();
        while((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject obj = new JSONObject(response.toString());
        return obj.getJSONArray("rates");
    }
}