package com.example.practicaljava.api.server;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.example.practicaljava.entity.Car;
import com.example.practicaljava.services.CarService;
import com.example.practicaljava.services.RandomCarService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CarApiTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void testGetCar() {
		webTestClient.get()
						.uri("/api/v1/car/buildNewRandom")
						.exchange()
						.expectBody(Car.class)
						.value(car -> {
							assertTrue(CarService.BRANDS.contains(car.getBrand()));
							assertTrue(CarService.COLORS.contains(car.getColor()));
						});
		//fail("Not yet implemented");
	}

//	@Test
	void testGetCollection() {
		//fail("Not yet implemented");
	}

//	@Test
	void testGetTotalCars() {
		//fail("Not yet implemented");
	}

//	@Test
	void testEcho() {
		//fail("Not yet implemented");
	}


	@Autowired
	@Qualifier("randomCarService")
	private RandomCarService carService;
	
	@Test
	void testSaveCar() {
		Car newCar = carService.buildCar();
		
		// Lets test for 10 cars
		for (int i=0; i<10; i++) {	
			
			System.out.println("Saving car " + i);
			
			assertTimeout(Duration.ofSeconds(1), () -> 
				webTestClient.post()
							.uri("/api/v1/car/saveCar")
							.contentType(MediaType.APPLICATION_JSON)
							.bodyValue(newCar)
							.exchange()
							.expectStatus()
							.is2xxSuccessful()
			);
		}

		//fail("Not yet implemented");
	}

//	@Test
	void testGetCarString() {
		//fail("Not yet implemented");
	}

//	@Test
	void testUpdateCar() {
		//fail("Not yet implemented");
	}

//	@Test
	void testFindCarByBrandAndColor() {
		//fail("Not yet implemented");
	}

//	@Test
	void testFindCarByPath() {
		//fail("Not yet implemented");
	}

//	@Test
	void testFindCarByColor() {
		//fail("Not yet implemented");
	}

//	@Test
	void testFindCarByAvailability() {
		//fail("Not yet implemented");
	}

//	@Test
	void testFindCarsReleasedAfter() {
		//fail("Not yet implemented");
	}

	@Test
	void testFindCarByParam() {
		final int pgSize = 5;
		
		// Our code only takes color param. but this can be done for brand and color param.
		//for ( var brand : carService.BRANDS) {
			for ( var color : carService.COLORS ) {
				webTestClient.get()
								.uri( uriBuilder -> uriBuilder.path("/api/v1/car/findCars")
																//.queryParam("brand", brand)
																.queryParam("color", color)
																.queryParam("page", 1)
																.queryParam("pgSize", pgSize)
																.build())
								.exchange()
								.expectBodyList(Car.class)
								.value( cars -> {
									assertNotNull(cars);
									assertTrue(cars.size() <= pgSize);
								});
			}
		//}
	}
}
