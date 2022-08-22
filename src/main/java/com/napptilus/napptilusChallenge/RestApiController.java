package com.napptilus.napptilusChallenge;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.napptilus.napptilusChallenge.entity.Price;
import com.napptilus.napptilusChallenge.entity.Product;
import com.napptilus.napptilusChallenge.repo.PricesRepository;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/napptilus")
public class RestApiController {

	@Autowired
	PricesRepository repository;

	@GetMapping("/retrieveOfferDetails")
	public ResponseEntity<List<Product>> getOfferDetail(@RequestParam Integer brand, @RequestParam Integer product,
			@RequestParam String date) {
		try {
			List<Price> listPrices = new ArrayList<Price>();
			List<Product> listProducts = new ArrayList<Product>();
			Date convertedDate = convertDate(date);

			if (convertedDate == null) {
				return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
			}

			repository.findPriceByBrandIdAndProductId(brand, product).forEach(listPrices::add);

			Optional<Price> price = listPrices.stream().filter(Objects::nonNull)
					.filter(p -> p.getEndDate() != null && p.getStartDate() != null)
					.filter(p -> convertedDate.after(p.getStartDate()) && convertedDate.before(p.getEndDate()))
					.max(Comparator.comparing(Price::getPriority));

			if (price.isPresent()) {
				listProducts.add(new Product(price.get()));
			} else {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(listProducts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
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
