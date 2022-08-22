package com.napptilus.napptilusChallenge;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.napptilus.napptilusChallenge.entity.Price;
import com.napptilus.napptilusChallenge.entity.Product;
import com.napptilus.napptilusChallenge.repo.PricesRepository;

@SpringBootTest
class NapptilusChallengeApplicationTests {

	@InjectMocks
	RestApiController controller;

	@Mock
	PricesRepository repository;

	@BeforeEach
	void beforeEach() {
		controller = new RestApiController();
		MockitoAnnotations.initMocks(this);
		when(repository.findPriceByBrandIdAndProductId(any(), any())).thenReturn(mockListPrices());

	}

	@Test
	void shouldReturnAProductOnDay14062022at10forProduct55245() {
		ResponseEntity<List<Product>> response = controller.getOfferDetail(1, 55245, "14-06-2022 10:00");

		asssertCorrectResponse(response);
		assertProduct(response.getBody().get(0), 100.10, 55245);
	}

	@Test
	void shouldReturnAProductOnDay14062022at16forProduct55245() {
		ResponseEntity<List<Product>> response = controller.getOfferDetail(1, 55245, "14-06-2022 16:00");

		asssertCorrectResponse(response);
		assertProduct(response.getBody().get(0), 30.50, 55245);
	}

	@Test
	void shouldReturnAProductOnDay14062022at21forProduct55245() {
		ResponseEntity<List<Product>> response = controller.getOfferDetail(1, 55245, "14-06-2022 21:00");

		asssertCorrectResponse(response);
		assertProduct(response.getBody().get(0), 100.1, 55245);
	}

	@Test
	void shouldReturnAProductOnDay15062022at10forProduct55245() {
		ResponseEntity<List<Product>> response = controller.getOfferDetail(1, 55245, "15-06-2022 10:00");

		asssertCorrectResponse(response);
		assertProduct(response.getBody().get(0), 100.10, 55245);
	}

	@Test
	void shouldReturnAProductOnDay16062022at21forProduct55245() {
		ResponseEntity<List<Product>> response = controller.getOfferDetail(1, 55245, "16-06-2022 21:00");

		asssertCorrectResponse(response);
		assertProduct(response.getBody().get(0), 10.0, 55245);
	}

	@Test
	void shouldReturnANotResultStatus() {
		ResponseEntity<List<Product>> response = controller.getOfferDetail(1, 55245, "16-06-2020 21:00");

		asssertInCorrectResponse(response, HttpStatus.NO_CONTENT);
	}

	@Test
	void shouldReturnAInternalServerErrorWhenBadFormattedDate() {
		ResponseEntity<List<Product>> response = controller.getOfferDetail(1, 55245, "16 Dec 2020 21:00");

		asssertInCorrectResponse(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private void asssertCorrectResponse(ResponseEntity<List<Product>> response) {
		assertNotNull(response);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertNotNull(response.getBody());
	}

	private void asssertInCorrectResponse(ResponseEntity<List<Product>> response, HttpStatus expectedStatus) {
		assertNotNull(response);
		assertEquals(response.getStatusCode(), expectedStatus);
		assertNull(response.getBody());
	}

	private void assertProduct(Product product, Double expectedPrice, Integer expectedId) {
		assertNotNull(product);
		assertEquals(product.getPrice(), expectedPrice);
		assertEquals(product.getProductId(), expectedId);
	}

	private List<Price> mockListPrices() {
		List<Price> mockedList = new ArrayList<Price>();

		mockedList.add(mockPrice("14-06-2022 00:00", "31-12-2022 23:59", 100.10, 0, 55245));
		mockedList.add(mockPrice("14-06-2022 15:00", "14-06-2022 18:30", 30.50, 1, 55245));
		mockedList.add(mockPrice("15-06-2022 10:00", "15-06-2022 20:00", 1000.00, 0, 55245));
		mockedList.add(mockPrice("15-06-2022 16:00", "31-12-2022 23:00", 10.00, 1, 55245));

		return mockedList;
	}

	private Price mockPrice(String startDate, String endDate, Double price, Integer priority, Integer productId) {
		Price p = new Price();

		p.setBrandId(1);
		p.setCurrency("EUR");
		p.setEndDate(convertDate(endDate));
		p.setStartDate(convertDate(startDate));
		p.setPrice(price);
		p.setPriceList(1);
		p.setPriority(priority);
		p.setProductId(productId);

		return p;
	}

	private Date convertDate(String date) {
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		try {
			return formatter.parse(date);
		} catch (ParseException e) {
			return null;
		}

	}

}
