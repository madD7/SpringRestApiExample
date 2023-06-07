package com.example.practicaljava.api.server;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.practicaljava.entity.CarPromotion;

import com.example.practicaljava.exception.IllegalApiParamException;
import com.example.practicaljava.repository.CarPromotionElasticRepository;
import com.example.practicaljava.services.CarPromotionService;

@RestController
@RequestMapping(value="/api/v1")
public class CarPromotionApi {
	
	@Autowired
	private CarPromotionService promoService;
	
	@Autowired
	private CarPromotionElasticRepository promoRepo;

	@GetMapping(value="/PromoList")
	public List<CarPromotion> getPromotionList(@RequestParam (name="type") String promoType,
												@RequestParam(defaultValue = "0") int page,
												@RequestParam(defaultValue = "5") int size){
		if ( !promoService.isValidPromotion(promoType)) {
			
			// Will not trigger our Exception, but default Spring error response.
			// @RestControllerAdvice class annotation, make class global exception handler.
			throw new IllegalApiParamException("Invalid Promotion type: " + promoType);
		}
		
		var pageable = PageRequest.of(page, size);
		return promoRepo.findByType(promoType, pageable).toList();
	}
}
