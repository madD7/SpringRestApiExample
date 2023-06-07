package com.example.practicaljava.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.example.practicaljava.entity.Car;

/** This is for accessing and modifying data in 
 * 	ElasticSearch.
 * 
 * With SpringData, we just need to create an 
 * interface and SpringData will automatically
 * handle the rest of the communication, including
 * creating proper query and data manipulation statements.
 * */
@Repository
public interface CarElasticRepository extends ElasticsearchRepository<Car, String> {
	/* ElasticsearchRepository<Car, String> - Class used for data storage = Car. 
		Datatype of Identifier used in car (Id) = String.
	
		Method to search Cars that match Brand and Color
		We Dont require method body. Spring can build query from method name.
		method name can start with 'findBy' or 'readBy'.
		
		We can also define custom query with Spring data using annotation @Query before method.
		And the method name can be anything, we don’t have to follows the Spring Data rule.
		This is discouraged though.
	*/ 
	public List<Car> findByBrandAndColor(String brand, String color);
	
	public List<Car> findByIsAvailable(boolean isAvailable);
	
	// public List<Car> findByColorOrType(String color, String type);
	
	public Page<Car> findByColor(String color, Pageable pagable);
	
	public List<Car> findByReleaseDateAfter(LocalDate date);
}
