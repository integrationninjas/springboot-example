package com.integrationninjas.springbootexample;

import com.integrationninjas.springbootexample.dto.BitcoinDataDto;
import com.integrationninjas.springbootexample.service.impl.CoinDeskServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class SpringbootExampleApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void getCurrentBitcoinData_shouldReturnLivePrice_whenCoinbaseResponseIsAvailable() {
		CoinDeskServiceImpl service = new CoinDeskServiceImpl();
		RestTemplate restTemplate = mock(RestTemplate.class);
		when(restTemplate.getForObject(anyString(), eq(String.class)))
				.thenReturn("{\"data\":{\"amount\":\"84133.545\",\"base\":\"BTC\",\"currency\":\"USD\"}}");
		ReflectionTestUtils.setField(service, "restTemplate", restTemplate);

		BitcoinDataDto result = service.getCurrentBitcoinData();

		assertNotNull(result);
		assertEquals("84133.545", result.getBpi().get("USD").getRate());
		assertEquals("Powered by Coinbase. Live BTC price.", result.getDisclaimer());
	}

	@Test
	void getCurrentBitcoinData_shouldReturnFallback_whenCoinbaseIsUnavailable() {
		CoinDeskServiceImpl service = new CoinDeskServiceImpl();
		RestTemplate restTemplate = mock(RestTemplate.class);
		when(restTemplate.getForObject(anyString(), eq(String.class)))
				.thenThrow(new HttpServerErrorException(HttpStatus.BAD_GATEWAY));
		ReflectionTestUtils.setField(service, "restTemplate", restTemplate);

		BitcoinDataDto result = service.getCurrentBitcoinData();

		assertNotNull(result);
		assertNotNull(result.getBpi());
		assertEquals("Fallback", result.getTime().getUpdated());
	}

}
