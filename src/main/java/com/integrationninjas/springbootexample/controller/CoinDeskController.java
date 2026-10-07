package com.integrationninjas.springbootexample.controller;

import com.integrationninjas.springbootexample.dto.BitcoinDataDto;
import com.integrationninjas.springbootexample.service.CoinDeskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/bitcoin")
public class CoinDeskController {

    @Autowired
    private CoinDeskService coinDeskService;

    @GetMapping("/currentPrice")
    public ResponseEntity<BitcoinDataDto> getCurrentBitcoinPrice() {
        BitcoinDataDto bitcoinData = coinDeskService.getCurrentBitcoinData();
        return ResponseEntity.ok(bitcoinData);
    }

}
