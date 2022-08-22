package com.napptilus.napptilusChallenge.entity;

import java.util.Date;

public class Product {

	private Integer productId;
	private Integer brandId;
	private Integer priceList;
	private Date startDate;
	private Date endDate;
	private Double price;

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getPriceList() {
		return priceList;
	}

	public void setPriceList(Integer priceList) {
		this.priceList = priceList;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Product(Price price) {
		super();
		this.productId = price.getProductId();
		this.brandId = price.getBrandId();
		this.priceList = price.getPriceList();
		this.startDate = price.getStartDate();
		this.endDate = price.getEndDate();
		this.price = price.getPrice();
	}

}
