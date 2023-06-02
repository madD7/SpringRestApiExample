package com.example.practicaljava.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/*
 * Disadvantage of IgnoreProperties:
 * Name in the value field must exactly match. 
 * Any changes in variable names, must be done here.
 * Use @JsonIgnore individually on the variables, instead. */
@JsonIgnoreProperties(value= {"torque", "serialNumber"}) 
public class Engine {
	private String fuelType;
	private int horsePower;
	
	// @JsonIgnore // fields ignored using @JsonIgnoreProperties
	private int serialNumber;
	private int torque;
	
	public Engine() {
		super();
	}

	public Engine(String fuelType, int horsePower) {
		super();
		this.fuelType = fuelType;
		this.horsePower = horsePower;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public int getHorsePower() {
		return horsePower;
	}

	public void setHorsePower(int horsePower) {
		this.horsePower = horsePower;
	}
	
	public int getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(int serialNumber) {
		this.serialNumber = serialNumber;
	}

	public int getTorque() {
		return torque;
	}

	public void setTorque(int torque) {
		this.torque = torque;
	}

	@Override
	public String toString() {
		return "Engine [fuelType=" + fuelType + ", horsePower=" + horsePower + ", serialNumber=" + serialNumber
				+ ", torque=" + torque + "]";
	}
}
