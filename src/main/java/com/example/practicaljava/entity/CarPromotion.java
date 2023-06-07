package com.example.practicaljava.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName="practicaljava_2")
public class CarPromotion {
	private String type;
	private String description;
	
	@Id
	private String id;
	
	public CarPromotion() {
		super();
	}

	public CarPromotion(String type, String description) {
		super();
		this.type = type;
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
