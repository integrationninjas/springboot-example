package com.integrationninjas.springbootexample.service;

import com.integrationninjas.springbootexample.dto.BitcoinDataDto;

public interface CoinDeskService {
    BitcoinDataDto getCurrentBitcoinData();
}
