package com.example.practicaljava.services;

import org.springframework.stereotype.Service;

@Service
public class DefaultPromotionService implements CarPromotionService {

	@Override
	public boolean isValidPromotion(String promotionType) {
		return PROMOTION_TYPES.contains(promotionType.toLowerCase());
	}

}
