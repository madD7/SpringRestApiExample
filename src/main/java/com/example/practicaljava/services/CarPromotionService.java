package com.example.practicaljava.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface CarPromotionService {
	
	List<String> PROMOTION_TYPES = new ArrayList(Arrays.asList("bonous", "discount", "subsidy"));
	
	boolean isValidPromotion(String promotionType);
}
