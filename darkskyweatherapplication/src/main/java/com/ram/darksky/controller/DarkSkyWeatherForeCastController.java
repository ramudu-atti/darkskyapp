package com.ram.darksky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ram.darksky.model.ForeCastResponse;
import com.ram.darksky.model.Location;
import com.ram.darksky.model.MapAPIResponse;
import com.ram.darksky.service.ForecastRetriever;
import com.ram.darksky.service.MapInfoRetriever;

@RestController
public class DarkSkyWeatherForeCastController {
	
	@RequestMapping("/testit")
	public ResponseEntity<String> getWeatherData(){
		return new ResponseEntity<String>("helloram", HttpStatus.OK);
		
	}

	/**
	 * Get weather forecast data by city and state, by deafault campbell, CA data will be loaded from darksky api
	 * @param city
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/getforecastData/{city},{state}", method=RequestMethod.GET, produces="application/json")
	public ForeCastResponse getForecast(@PathVariable("city") String city,
			@PathVariable("state") String state) {
		System.out.println("Input entered......");
		
		/**MapAPIResponse mapResponse = this.getMapInfoRetriever().getMapInfoFor(city, state);
		
		System.out.println("java......");
		Location loc = mapResponse.getGeometry().getLocation();
		ForeCastResponse forecastResponse = forecastRetriever.getForcastFor(String.valueOf(loc.getLatitude()),
				String.valueOf(loc.getLongitude()));
		**/
		
		ForeCastResponse forecastResponse = forecastRetriever.getForcastFor(String.valueOf(37.2872),
				String.valueOf(-121.949959));
		System.out.println("got forecast response......");
		//forecastResponse.setFormattedAddress(mapResponse.getFormattedAddress());
		forecastResponse.setFormattedAddress("1600 Campbell, CA ");
		forecastResponse.setSearchAddress(buildSearchAddress(city,state));
		return forecastResponse;
	}

	@RequestMapping(value = "/getforecastDataByCoordinates/{latitude},{longitude}", method=RequestMethod.GET, produces="application/json")
	public ForeCastResponse getforecastDataByCoordinates(@PathVariable("latitude") String latitude,
			@PathVariable("longitude") String longitude) {
		System.out.println("Input entered......"+latitude+ " , "+longitude);
		
		/**MapAPIResponse mapResponse = this.getMapInfoRetriever().getMapInfoFor(city, state);
		
		System.out.println("java......");
		Location loc = mapResponse.getGeometry().getLocation();
		ForeCastResponse forecastResponse = forecastRetriever.getForcastFor(String.valueOf(loc.getLatitude()),
				String.valueOf(loc.getLongitude()));
		**/
		
		ForeCastResponse forecastResponse = forecastRetriever.getForcastFor(String.valueOf(latitude),
				String.valueOf(longitude));
		System.out.println("got forecast response......");
		//forecastResponse.setFormattedAddress(mapResponse.getFormattedAddress());
		//forecastResponse.setFormattedAddress("1600 Campbell, CA ");
		//forecastResponse.setSearchAddress(buildSearchAddress(city,state));
		return forecastResponse;
	}

	String buildSearchAddress(String city, String state) {
		StringBuilder builder = new StringBuilder();
		builder.append(city);
		builder.append(",");
		builder.append(state);
		return builder.toString();
	}
	
	
	@Autowired
	private ForecastRetriever forecastRetriever;

	@Autowired
	private MapInfoRetriever mapInfoRetriever;
	
	public MapInfoRetriever getMapInfoRetriever() {
		return mapInfoRetriever;
	}

	public void setMapInfoRetriever(MapInfoRetriever mapInfoRetriever) {
		this.mapInfoRetriever = mapInfoRetriever;
	}

	public ForecastRetriever getForecastRetriever() {
		return forecastRetriever;
	}

	public void setForecastRetriever(ForecastRetriever forecastRetriever) {
		this.forecastRetriever = forecastRetriever;
	}
}
