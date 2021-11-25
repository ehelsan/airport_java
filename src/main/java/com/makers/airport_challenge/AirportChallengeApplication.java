package com.makers.airport_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.Random;

@SpringBootApplication
public class AirportChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirportChallengeApplication.class, args);
	}

	ArrayList<String> planes = new ArrayList<>();
	int defaultCapacity = 10;

	public boolean isAtAirport (String plane){
		return planes.contains(plane);
	}


	public String randomWeather() {

		ArrayList<String> weathers = new ArrayList<>();
		weathers.add("stormy");
		weathers.add("sunny");
		weathers.add("overcast");
		weathers.add("windy");

		Random rand = new Random();
		int random = rand.nextInt(weathers.size());
		return weathers.get(random);
	}

	public String landPlane(String plane){
		if(planes.size() < defaultCapacity && !isAtAirport(plane)) {
			planes.add(plane);
			return "Landing successful.";
		} if(randomWeather().equals("stormy")) {
			return "Cannot land plane due to stormy weather, await further instructions.";
		} if(isAtAirport(plane)){
			return "Unable to land plane as it is already at the airport.";
		}
		return "Unable to land plane, Airport is at capacity.";
	}

	public String takeOff(String plane){

		if(!randomWeather().equals("stormy") && isAtAirport(plane)) {
			planes.remove(plane);
			return "Take-off successful.";
		} if (!isAtAirport(plane)){
			return "Airplane is no longer at the airport. Please select another aircraft for take-off.";
		}
		return "Take-off blocked due to stormy weather, please wait for further updates.";
	}

}
