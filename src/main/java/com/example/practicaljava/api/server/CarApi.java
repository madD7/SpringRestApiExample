package com.example.practicaljava.api.server;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practicaljava.entity.Car;
import com.example.practicaljava.repository.CarElasticRepository;
import com.example.practicaljava.services.CarService;

@RestController
@RequestMapping(value="/api/v1/car")
public class CarApi {
	private static final Logger LOG = LoggerFactory.getLogger(CarApi.class);
	
	@Autowired
	private CarService carService;
	
	@Autowired
	private CarElasticRepository carRepo;

	// @RequestMapping(value="/buildNewRandom", method=RequestMethod.Get) - is also a valid syntax.
	@GetMapping(value="/buildNewRandom", produces=MediaType.APPLICATION_JSON_VALUE)
	public Car getCar() {
		// Spring will automatically convert Java Object into Json.
		// Hence we dont explicitly convert car object to json.
		LOG.info("New car built successfully.");
		return carService.buildCar();
	}
	
	@GetMapping(value="/getCarCollection", produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Car> getCollection(){
		int rnd = ThreadLocalRandom.current().nextInt(0,10);
		List<Car> cars = new ArrayList<>();
		
		for (int i=0; i<rnd; i++)
			cars.add(getCar());
		
		// LOG.info("Car collection built successfully.");
		return cars;
	}
	
	@GetMapping(value="/getTotalCars")
	public String getTotalCars() {
		String msg = "There are " + carRepo.count() + " cars.";
		// LOG.info(msg);
		return msg;
	}
	
	@PostMapping(value="/echo")
	public String echo(@RequestBody Car car) {
		LOG.info("Received: ", car);
		return car.toString();
	}
	
	/* Following actions are performed on 
	 * Elastic search repository */
	@PostMapping(value="/saveCar", consumes=MediaType.APPLICATION_JSON_VALUE)
	public String saveCar(@RequestBody Car car) {
		var id = carRepo.save(car).getId();
		String msg = "Saveed car details with Id: " + id; 
		// LOG.info(msg);
		return msg;
	}

	// Search car by Id. The Id variable is passed in url.
	@GetMapping(value="{id}")
	public Car getCar(@PathVariable("id") String carId ) {
		// LOG.info("Fetching car details for car Id: " + carId);
		return carRepo.findById(carId).orElse(null);
	}
	
	@PutMapping(value="/{id}")
	public String updateCar(@PathVariable("id") String carId, @RequestBody Car carDetails) {
		carDetails.setId(carId);
		var newCar = carRepo.save(carDetails);
		String msg = "Updated car Id: {}" + newCar.getId(); 
		// LOG.info(msg);
		return msg;
	}
	
	// We take input object of type Car. 
	// Else we will have to create a separate class with fields: Brand and Color 
	@GetMapping(value="/findCars",
				consumes=MediaType.APPLICATION_JSON_VALUE,
				produces=MediaType.APPLICATION_JSON_VALUE)
	public List<Car> findCarByBrandAndColor(@RequestBody Car car){
		return carRepo.findByBrandAndColor(car.getBrand(), car.getColor());
	}
	
	// "/FindCars" is static Path.
	// {brand} & {color} are variables. - Each variable is seperated by "/" and enclosed in {}.
	@GetMapping(value="/findCars/{brand}/{color}")
	public List<Car> findCarByPath(@PathVariable String brand, @PathVariable String color){
		return carRepo.findByBrandAndColor(brand, color);
	}
	
	// Optional Path parameter example
	/*@GetMapping(path={"/findCars/{color}/{type}", "/findCars/{color}"})
	public List<Car> findCarByOptionalType(@PathVariable String color,
									@PathVariable(name="type", required=false) Optional<String> type){
		List<Car> cars = null;
		
		if ( type.isPresent() == true )
			cars = carRepo.findByColorOrType(color, type.get());
		else
			cars = carRepo.findByColor(color);
		
		return cars;
	}*/
	
	@GetMapping(path="/findCars/color")
	public List<Car> findCarByColor(@RequestParam String color, 
									@RequestParam(defaultValue = "0") int page,
									@RequestParam(defaultValue = "10") int pgSize){
		// Sort parameter is optional.
		Pageable pageable = PageRequest.of(page, pgSize, Sort.by(Direction.DESC, "price"));
		return carRepo.findByColor(color, pageable).getContent();
	}
	
	@GetMapping(path="/findCars/{isAvailable}")
	public List<Car> findCarByAvailability(@PathVariable boolean isAvailable){
		return carRepo.findByIsAvailable(isAvailable);
	}
	
	@GetMapping(path="/findCars/date")
	public List<Car> findCarsReleasedAfter(@RequestParam(name="release_date") 
											@DateTimeFormat(pattern="yyyy-MM-dd") LocalDate releaseDate){
		return carRepo.findByReleaseDateAfter(releaseDate);
	}
	
	// Request parameters, also known as Query parameters, are not part of url. 
	// URL path is terminated by "?", followed by Query parametes in "key=value" pairs.
	// Eg: http://localhost:8001/api/v1/car/cars?brand=random&color=black
	// Name of query parameter must match the parameter name in Java getter/setter method. 
	@GetMapping(value="/findCars")
	public List<Car> findCarByParam(@RequestParam String brand, @RequestParam String color){
		return carRepo.findByBrandAndColor(brand, color);
	}
	
}
