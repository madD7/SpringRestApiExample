package com.example.practicaljava.api.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.practicaljava.entity.Car;
import com.example.practicaljava.services.CarService;

@RestController
@RequestMapping(value="/api/v1/car")
public class CarApi {
	private static final Logger LOG = LoggerFactory.getLogger(CarApi.class);
	
	@Autowired
	private CarService carService;

	// @RequestMapping(value="/buildNewRandom", method=RequestMethod.Get) - is also a valid syntax.
	@GetMapping(value="/buildNewRandom", produces=MediaType.APPLICATION_JSON_VALUE)
	public Car getCar() {
		// Spring will automatically convert Java Object into Json.
		// Hence we dont explicitly convert car object to json.
		return carService.buildCar();
	}
	
	@GetMapping(value="/getCarCollection", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Car> getCollection(){
		int rnd = ThreadLocalRandom.current().nextInt(0,10);
		List<Car> cars = new ArrayList<>();
		
		for (int i=0; i<rnd; i++)
			cars.add(getCar());
		
		return cars;
	}
	
	@PostMapping(value="/echo")
	public String echo(@RequestBody Car car) {
		LOG.info("Received: ", car);
		return car.toString();
	}
	

}
