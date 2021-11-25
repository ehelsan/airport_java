package com.makers.airport_challenge;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;


import static org.assertj.core.api.Assertions.*;



@SpringBootTest
class AirportChallengeApplicationTests {

    AirportChallengeApplication airport = new AirportChallengeApplication();

//	As an air traffic controller
//	So I can get passengers to a destination
//	I want to instruct a plane to land at an airport

    @Test
    public void canLandPlane() {
        airport.landPlane("First Plane");
        assertThat(airport.isAtAirport("First Plane"));
        Assertions.assertEquals(1, airport.planes.size());
    }


//    As an air traffic controller
//    So I can get passengers on the way to their destination
//    I want to instruct a plane to take off from an airport and confirm that it is no longer in the airport

    @Test
    void canTakeOff(){
        airport.landPlane("Plane Two");
        airport.takeOff("Plane Two");
        assertThat(!airport.isAtAirport("Plane Two"));
    }


//    As an air traffic controller
//    To ensure safety
//    I want to prevent landing when the airport is full

    @Test
    void canPreventLanding(){
        final AirportChallengeApplication airport = Mockito.spy(new AirportChallengeApplication());

        //can land due to good weather, max capacity has not been reached & plane is not already in the airport
        Mockito.when(airport.randomWeather()).thenReturn("overcast");
        Assertions.assertEquals("Landing successful.", airport.landPlane("Skippy"));

        //can't land a particular plane because it is already at the Airport
        Mockito.when(airport.randomWeather()).thenReturn("windy");
        Assertions.assertEquals("Landing successful.", airport.landPlane("Ishani"));
        Assertions.assertEquals("Unable to land plane as it is already at the airport.", airport.landPlane("Ishani"));

        //can't land in good weather because capacity limit is reached
        Mockito.when(airport.randomWeather()).thenReturn("sunny");
        for(int i=0; i < airport.defaultCapacity; i++){
            airport.landPlane("plane #" + i);
        }
        Assertions.assertEquals("Unable to land plane, Airport is at capacity.", airport.landPlane("Other plane"));
        Assertions.assertEquals(airport.defaultCapacity, airport.planes.size());


        //can't land due to stormy weather even though max capacity has not been reached
        Mockito.when(airport.randomWeather()).thenReturn("sunny");
        for(int i=0; i < airport.defaultCapacity; i++){
            airport.landPlane("Dusty #" + i);
        }
        airport.takeOff("Dusty");
        Mockito.when(airport.randomWeather()).thenReturn("stormy");
        Assertions.assertEquals("Cannot land plane due to stormy weather, await further instructions.", airport.landPlane("Leadbottom"));


    }


//    As an air traffic controller
//    To ensure safety
//    I want to prevent take-off when weather is stormy

    @Test
    void canPreventTakeOff(){

        final AirportChallengeApplication airport = Mockito.spy(new AirportChallengeApplication());

        //prevents take-off when stormy
        airport.landPlane("Plane 3");
        Mockito.when(airport.randomWeather()).thenReturn("stormy");
        Assertions.assertEquals("Take-off blocked due to stormy weather, please wait for further updates.", airport.takeOff("Plane 3"));

        //prevents take-off is the plane is not at the airport
        Mockito.when(airport.randomWeather()).thenReturn("windy");
        Assertions.assertEquals("Airplane is no longer at the airport. Please select another aircraft for take-off.", airport.takeOff("Rochelle"));
    }

}
