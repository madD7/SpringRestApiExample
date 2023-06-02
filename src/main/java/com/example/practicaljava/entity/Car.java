package com.example.practicaljava.entity;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import com.example.practicaljava.util.RandomDateGenerator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

@Document(indexName = "practicaljava") 
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // Set Globally in Application.yml
public class Car {
	
	@Id
	private String id;	// For ElasticSearch. Each ElasticSearch Entity must have unique Id.
	//Spring automatically maps datatypes, String type variable is mapped to String and numeric is mapped to numeric.
	
	private String brand;
	private String color;
	private String type;
	private int price;
	private boolean isAvailable;
	
	@Field(type=FieldType.Date, format=DateFormat.date)
	@JsonFormat(pattern="yyyy-MM-dd", timezone="Asia/Kolkata")		// Date is in ISO format for ElasticSearch
	private LocalDate releaseDate;		// Date field mapping is an issue here.
	
	@JsonInclude(value = Include.NON_EMPTY)
	private List<String> features;
	
	@JsonInclude(value = Include.NON_EMPTY)
	private String secretFeature;
	
	@JsonUnwrapped	// Engine attributes will be shown as Car attributes in Json.
	private Engine engine;
	private List<Tyre> tyres;
	
	public Car() {
		super();
	}

	public Car(String brand, String color, String type, int price) {
		super();
		this.brand = brand;
		this.color = color;
		this.type = type;
		this.price = price;
		this.isAvailable = ThreadLocalRandom.current().nextBoolean();
		this.releaseDate = RandomDateGenerator.getRandomDate();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public LocalDate getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(LocalDate releaseDate) {
		this.releaseDate = releaseDate;
	}

	public List<String> getFeatures() {
		return features;
	}

	public void setFeatures(List<String> features) {
		this.features = features;
	}
	
	public String getSecretFeature() {
		return secretFeature;
	}

	public void setSecretFeature(String secretFeature) {
		this.secretFeature = secretFeature;
	}

	public Engine getEngine() {
		return engine;
	}

	public void setEngine(Engine engine) {
		this.engine = engine;
	}

	public List<Tyre> getTyres() {
		return tyres;
	}

	public void setTyres(List<Tyre> tyres) {
		this.tyres = tyres;
	}

	@Override
	public String toString() {
		return "Car [brand=" + brand + ", color=" + color + ", type=" + type + ", price=" + price + ", isAvailable="
				+ isAvailable + ", releaseDate=" + releaseDate + ", features=" + features + ", secretFeature="
				+ secretFeature + ", engine=" + engine + ", tyres=" + tyres + "]";
	}

}
