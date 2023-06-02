package com.example.practicaljava.repository;

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
	// ElasticsearchRepository<Car, String> - Class used for data storage = Car. 
	// Datatype of Identifier used in car (Id) = String.

}
