package com.example.practicaljava.services;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.practicaljava.entity.Car;


public interface CarService {
	
	// new ArrayList is java8 compatible. List.of can be used for higher java versions.
	
	List<String> BRANDS = new ArrayList<>(Arrays.asList("Tata", "Mahindra", "Maruti", "HM", "LandRover"));
	List<String> COLORS = new ArrayList<>(Arrays.asList("White", "Black", "Grey", "Red", "Blue", "Silver"));
	List<String> TYPES = new ArrayList<>(Arrays.asList("Coupe", "Sedan", "SUV", "Luxury", "Convertible"));
	List<String> FEATURES = new ArrayList<>(Arrays.asList("Sunroof", "ABS", "DRLs", "RearCamera", "GPS"));
	List<String> FUELS = new ArrayList<>(Arrays.asList("Petrol", "Electric", "Hybrid", "Diesel", "Hydrogen"));
	List<String> TYRE_MANUFACTURERS = new ArrayList<>(Arrays.asList("GoodYear", "MRF", "Ceat" ));
	
	Car buildCar();
}
