package com.example.practicaljava.services;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.stereotype.Service;

import com.example.practicaljava.entity.Car;
import com.example.practicaljava.entity.Engine;
import com.example.practicaljava.entity.Tyre;

@Service
public class RandomCarService implements CarService {

	@Override
	public Car buildCar() {
		/* The ThreadLocalRandom uses the Java ThreadLocal construct 
		 * to create a new Random variable for each thread. 
		 * This guarantees that the calls from each thread 
		 * will never conflict with each (no contention).
		 * Hence, used instead of java.util.Random or 
		 * Math.random() - which creates instance of java.util.Random. 
		 */
		var brand = BRANDS.get(ThreadLocalRandom.current().nextInt(0, BRANDS.size()));
		var color = COLORS.get(ThreadLocalRandom.current().nextInt(0, COLORS.size()));
		var type = TYPES.get(ThreadLocalRandom.current().nextInt(0, TYPES.size()));
		
		var price = ThreadLocalRandom.current().nextInt(10000,Short.MAX_VALUE);
		
		int randCount = ThreadLocalRandom.current().nextInt(0, FEATURES.size());
		
		List<String> features = new ArrayList<>();
		for (int i=0; i < randCount; i++) {
			String tmp = FEATURES.get(ThreadLocalRandom.current().nextInt(0, FEATURES.size()));
			
			if (features.contains(tmp) == false)
				features.add(tmp);
			else
				i--;
		}
		
		String secretFeature = ThreadLocalRandom.current().nextBoolean() == true ? secretFeature = "AutoAIDirve" : null;
		
		var fuel = FUELS.get(ThreadLocalRandom.current().nextInt(0, FUELS.size()));
		int torque = ThreadLocalRandom.current().nextInt(1000,10000);
		int serialNo = ThreadLocalRandom.current().nextInt(10000,Integer.MAX_VALUE);
		Engine engine = new Engine(fuel, ThreadLocalRandom.current().nextInt(10,Byte.MAX_VALUE));
		
		// Torque and serialNumber will not be displayed in output.
		engine.setTorque(torque);
		engine.setSerialNumber(serialNo);
		
		List<Tyre> tyres = new ArrayList<>();
		for (int i=0; i<4; i++) {
			var manufacturer = TYRE_MANUFACTURERS.get(ThreadLocalRandom.current().nextInt(0, TYRE_MANUFACTURERS.size()));
			int size = ThreadLocalRandom.current().nextInt(12,20);
			int cost = ThreadLocalRandom.current().nextInt(1000,10000);
			tyres.add(new Tyre(manufacturer, size, cost));
		}
		
		Car tempCar = new Car(brand, color, type, price); 
		tempCar.setFeatures(features);
		tempCar.setEngine(engine);
		tempCar.setTyres(tyres);
		tempCar.setSecretFeature(secretFeature);
		
		return tempCar;
	}

}
