package com.example.practicaljava.common;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.practicaljava.entity.Car;
import com.example.practicaljava.repository.CarElasticRepository;
import com.example.practicaljava.services.RandomCarService;

@Component
public class CarElasticDatasource {

	private static final Logger LOG = LoggerFactory.getLogger(CarElasticDatasource.class);
	
	@Autowired
	private CarElasticRepository carRepository;
	
	@Autowired
	@Qualifier("randomCarService")
	private RandomCarService carService;
	
	@Autowired
	@Qualifier("webClientElasticsearch")
	private WebClient webClient;
	
	@EventListener(ApplicationReadyEvent.class)
	public void populateData() {
		
		var response = webClient.delete()
								.uri("/practicaljava")
								.retrieve()
								.bodyToMono(String.class)
								.block();
					
		LOG.info("End delete with response() {}", response);
		
		var cars = new ArrayList<Car>();
		
		for (int i=0; i< 10_000; i++) {
			cars.add(carService.buildCar());
		}
		
		carRepository.saveAll(cars);
		LOG.info("Saved {} cars.", carRepository.count());
	}
	
}
