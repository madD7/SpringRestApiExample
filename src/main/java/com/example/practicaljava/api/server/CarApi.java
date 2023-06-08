package com.example.practicaljava.api.server;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practicaljava.api.response.ErrorResponse;
import com.example.practicaljava.entity.Car;
import com.example.practicaljava.exception.IllegalApiParamException;
import com.example.practicaljava.repository.CarElasticRepository;
import com.example.practicaljava.services.CarService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(value="/api/v1/car")
@Tag(name = "Car REST API",
		description = "Car REST APIs for demonstrating CRUD operations on Cars with different Brand, Color, Fuel Types having different Features. ")
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
	
	/* "/FindCars" is static Path.
	* {brand} & {color} are variables. - Each variable is seperated by "/" and enclosed in {}.
	* The datatype verfication is required. if datatype is not string, then a error must be sent to server.
	* For that the return type is changed from List<Car> to ResponseEntity. */
	@GetMapping(value="/findCars/{brand}/{color}")
	public ResponseEntity<Object> findCarByPath(@PathVariable String brand, @PathVariable String color){
		var headers = new HttpHeaders();
		headers.add(HttpHeaders.SERVER, "Spring Server Header");
		headers.add("CustomHeader", "Custom Response Header");
		
		if ( StringUtils.isNumeric(brand) ) {
			var errResponse = new ErrorResponse("Invalid brand input: " + brand, LocalDateTime.now());
			return new ResponseEntity<Object>(errResponse, headers, HttpStatus.BAD_REQUEST);
		}
		
		if ( StringUtils.isNumeric(color) ) {
			var errResponse = new ErrorResponse("Invalid color input: " + color, LocalDateTime.now());
			return new ResponseEntity<Object>(errResponse, headers, HttpStatus.BAD_REQUEST);
		}
		
		var cars = carRepo.findByBrandAndColor(brand, color);
		
		return ResponseEntity.ok().headers(headers).body(cars);
	}
	
	// Optional Path parameter example
	// Two Urls with same datatype but different parameters will result to compilation errors.
	@GetMapping(path={"/findCars/optional/{color}/{type}", "/findCars/optional/{color}"})
	public List<Car> findCarByOptionalType(@PathVariable String color,
									@PathVariable(name="type", required=false) Optional<String> type){
		List<Car> cars = null;
		
		if ( type.isPresent() == true )
			cars = carRepo.findByColorOrType(color, type.get());
		else {
			Pageable pageable = PageRequest.of(0, 10);
			cars = carRepo.findByColor(color, pageable).getContent();
		}
		
		return cars;
	}

	
	// @ApiResponses - documentation for list of responses.
	@Operation(summary="Search for Cars on basis of Color.",
			description="Give input color. Eg: /api/v1/car/findCars/color?color=\"White\".")
	@ApiResponses({
		@ApiResponse(responseCode="200", description="Query executed successfully."),
		@ApiResponse(responseCode="400", description="Invalid input parameter.")
	})
	@GetMapping(path="/findCars/color")
	public List<Car> findCarByColor(@Parameter(description="Color of Car.", example="Blue") @RequestParam String color, 
									@Parameter(description="Page number (for Pagenation).")@RequestParam(defaultValue = "0") int page,
									@Parameter(description="Cars to be displayed per page.")@RequestParam(defaultValue = "10") int pgSize){
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
		
		if (StringUtils.isNumeric(color)) {
			// We can also create custom exception.
			throw new IllegalArgumentException("Invalid color: " + color);
		}
		
		if (StringUtils.isNumeric(brand)) {
			throw new IllegalApiParamException("Invalid brand: " + brand);
		}
		
		return carRepo.findByBrandAndColor(brand, color);
	}
	
	/* Method to handle Invalid Color Exception. 
	 * This method will send response with error message. */
	/* Methods marked with @ExceptionHandler will only handle the 
	 * exceptions thrown within its own class.
	 * */
	@ExceptionHandler(value = IllegalArgumentException.class)
	private ResponseEntity<ErrorResponse> handleInvalidColorException(IllegalArgumentException e) {
		LOG.warn(e.getMessage());
		var errResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		
		// This is ResponseEntity Constructor way
		return new ResponseEntity<ErrorResponse>(errResponse, null, HttpStatus.BAD_REQUEST);
		
		// Below is ResponseEntity builder-MethodChaining way 
		// return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);   		// is also valid.
	}
	
	/** Moved to GlobalExceptionHandler class */
	/** 
	@ExceptionHandler(value = IllegalApiParamException.class)
	private ResponseEntity<ErrorResponse> handleIllegalApiParamException(IllegalApiParamException e) {
		LOG.warn(e.getMessage());
		var errResponse = new ErrorResponse(e.getMessage(), LocalDateTime.now());
		
		// This is ResponseEntity Constructor way
		// return new ResponseEntity<ErrorResponse>(errResponse, null, HttpStatus.BAD_REQUEST);
		
		// Below is ResponseEntity builder-MethodChaining way 
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errResponse);   		// is also valid.
	}*/
}
