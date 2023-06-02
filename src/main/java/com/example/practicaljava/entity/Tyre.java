package com.example.practicaljava.entity;

public class Tyre {
	private String manufacturer;
	private int size;
	
	private int price;
	
	public Tyre() {
		super();
	}

	public Tyre(String manufacturer, int size, int price) {
		super();
		this.manufacturer = manufacturer;
		this.size = size;
		this.price = price;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Tyre [manufacturer=" + manufacturer + ", size=" + size + ", price=" + price + "]";
	}

}
