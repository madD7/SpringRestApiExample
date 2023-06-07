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
import com.example.practicaljava.entity.CarPromotion;
import com.example.practicaljava.repository.CarElasticRepository;
import com.example.practicaljava.repository.CarPromotionElasticRepository;
import com.example.practicaljava.services.DefaultPromotionService;
import com.example.practicaljava.services.RandomCarService;

@Component
public class CarPromotionElasticDatasource {

	private static final Logger LOG = LoggerFactory.getLogger(CarPromotionElasticDatasource.class);
	
	@Autowired
	private CarPromotionElasticRepository carPromotionRepository;
	
	@Autowired
	@Qualifier("defaultPromotionService")
	private DefaultPromotionService carPromotionService;
	
	@Autowired
	@Qualifier("webClientElasticsearch")
	private WebClient webClient;
	
	@EventListener(ApplicationReadyEvent.class)
	public void populateData() {
		carPromotionRepository.deleteAll();
		
		var carPromotions = new ArrayList<CarPromotion>(); 
				
		var promo1 = new CarPromotion();
		promo1.setType("bonus");
		promo1.setDescription("Purchase with cash and get free 2N3D trip for two people.");
		
		var promo2 = new CarPromotion();
		promo2.setType("bonus");
		promo2.setDescription("Purchase Luxury edition and get gift vouchers for hotel stay and resturants.");

		var promo3 = new CarPromotion();
		promo3.setType("bonus");
		promo2.setDescription("Purchase two cars and get 10gm Gold coin. ");

		var promo4 = new CarPromotion();
		promo4.setType("discount");
		promo4.setDescription("Purchase before end of month and get 5% discount.");
		
		var promo5 = new CarPromotion();
		promo5.setType("discount");
		promo5.setDescription("Purchase and book with 50% payment and get additional 1% discount.");

		var promo6 = new CarPromotion();
		promo6.setType("discount");
		promo6.setDescription("Purchase Convertible and get 10% discount.");
		
		var promo7 = new CarPromotion();
		promo7.setType("subsidy");
		promo7.setDescription("Purchase Electric and get 20% subsidy.");
		
		carPromotions.add(promo1);
		carPromotions.add(promo2);
		carPromotions.add(promo3);
		carPromotions.add(promo4);
		carPromotions.add(promo5);
		carPromotions.add(promo6);
		carPromotions.add(promo7);
		
		carPromotionRepository.saveAll(carPromotions);
		LOG.info("Saved {} promotions", carPromotionRepository.count());
	}
	
}
