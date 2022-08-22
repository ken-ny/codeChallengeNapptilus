package com.napptilus.napptilusChallenge.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.napptilus.napptilusChallenge.entity.Price;

@Service
public interface PricesRepository extends JpaRepository<Price, Long> {
	List<Price> findPriceByBrandIdAndProductId(Integer brandId, Integer productId);
}
