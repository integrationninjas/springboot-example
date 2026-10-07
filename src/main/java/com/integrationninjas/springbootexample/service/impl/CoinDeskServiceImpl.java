package com.integrationninjas.springbootexample.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.integrationninjas.springbootexample.dto.BitcoinDataDto;
import com.integrationninjas.springbootexample.service.CoinDeskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class CoinDeskServiceImpl implements CoinDeskService {

    private static final Logger logger = LoggerFactory.getLogger(CoinDeskServiceImpl.class);
    private static final String API_URL = "https://api.coinbase.com/v2/prices/BTC-USD/spot";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public BitcoinDataDto getCurrentBitcoinData() {
        try {
            String response = restTemplate.getForObject(API_URL, String.class);
            if (response != null && !response.isBlank()) {
                return parseCoinbaseResponse(response);
            }
        } catch (RestClientException | JsonProcessingException ex) {
            logger.warn("Coinbase API call failed, returning fallback data: {}", ex.getMessage());
        }

        return fallbackBitcoinData();
    }

    private BitcoinDataDto parseCoinbaseResponse(String response) throws JsonProcessingException {
        JsonNode root = new ObjectMapper().readTree(response);
        JsonNode amountNode = root.path("data").path("amount");
        String amount = amountNode.asText();

        if (amount == null || amount.isBlank()) {
            return fallbackBitcoinData();
        }

        BitcoinDataDto dto = new BitcoinDataDto();
        dto.setDisclaimer("Powered by Coinbase. Live BTC price.");
        dto.setChartName("Bitcoin");

        BitcoinDataDto.Time time = new BitcoinDataDto.Time();
        time.setUpdated("Live");
        dto.setTime(time);

        Map<String, BitcoinDataDto.CurrencyData> bpi = new HashMap<>();
        BitcoinDataDto.CurrencyData usd = new BitcoinDataDto.CurrencyData();
        usd.setCode("USD");
        usd.setSymbol("$");
        usd.setRate(amount);
        usd.setDescription("United States Dollar");
        try {
            usd.setRate_float(Double.parseDouble(amount));
        } catch (NumberFormatException ignored) {
            usd.setRate_float(0.0d);
        }
        bpi.put("USD", usd);
        dto.setBpi(bpi);

        return dto;
    }

    private BitcoinDataDto fallbackBitcoinData() {
        BitcoinDataDto dto = new BitcoinDataDto();
        dto.setDisclaimer("Coinbase API could not be reached in this environment. Showing fallback values.");
        dto.setChartName("Bitcoin");

        BitcoinDataDto.Time time = new BitcoinDataDto.Time();
        time.setUpdated("Fallback");
        dto.setTime(time);

        Map<String, BitcoinDataDto.CurrencyData> bpi = new HashMap<>();
        BitcoinDataDto.CurrencyData usd = new BitcoinDataDto.CurrencyData();
        usd.setCode("USD");
        usd.setSymbol("$");
        usd.setRate("0.00");
        usd.setDescription("United States Dollar");
        usd.setRate_float(0.0d);
        bpi.put("USD", usd);
        dto.setBpi(bpi);

        return dto;
    }
}
