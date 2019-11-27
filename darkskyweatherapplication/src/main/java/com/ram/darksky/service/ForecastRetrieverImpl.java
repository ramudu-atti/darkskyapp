package com.ram.darksky.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ram.darksky.model.ForeCastResponse;
import com.ram.darksky.utils.ExternalServiceGatewayException;
import com.ram.darksky.utils.ExternalServiceInvocationException;


@Service
public class ForecastRetrieverImpl implements ForecastRetriever {

	public static final String FORECAST_IO_SERVICE_NAME = "ForecastIOException";
	protected static final String ARG_LATITUDE = "latitude";
	protected static final String ARG_LONGITUDE = "longitude";
	protected static final String ARG_API_KEY = "apiKey";

	@Autowired
	private Environment environment;
	
	private String darkskyApiKey;

	@Value("${darksky.base.url}")
	private String darkskyBaseUrl;
	
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Autowired
	private RestTemplate restTemplate;
	
	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getDarkskyApiKey() {
		if (darkskyApiKey == null) {
			this.darkskyApiKey = environment.getProperty("DARKSKY_API_KEY");
		}
		return darkskyApiKey;
	}
	
	public void setDarkskyApiKey(String forecastioApiKey) {
		this.darkskyApiKey = forecastioApiKey;
	}

	public String getDarkskyBaseUrl() {
		return darkskyBaseUrl;
	}

	public void setDarkskyBaseUrl(String forecastioBaseUrl) {
		this.darkskyBaseUrl = forecastioBaseUrl;
	}

	Map<String,String> buildURLMap(String longitude, String latitude) {
		Map<String,String> arguments = new HashMap<String,String>();
		arguments.put(ARG_API_KEY, getDarkskyApiKey());
		arguments.put(ARG_LONGITUDE, longitude);
		arguments.put(ARG_LATITUDE, latitude);
		return arguments;
	}
	
	
	@Override
	public com.ram.darksky.model.ForeCastResponse getForcastFor(String longitude, String latitude) {
		try {
		ForeCastResponse forecast = (ForeCastResponse) restTemplate.getForObject(getDarkskyBaseUrl(), 
				ForeCastResponse.class, buildURLMap(longitude, latitude));
		return forecast;
		} catch (HttpStatusCodeException httpStatusEx) {
			// Darksky only return HTTPStatus code (not error response) so catch exceptions here and convert to 
			// our common Exception for easier error handling
//			System.out.println("HttpStatus from ForecastIO is: " + httpStatusEx.getRawStatusCode());
			throw new ExternalServiceInvocationException(FORECAST_IO_SERVICE_NAME, httpStatusEx.getRawStatusCode());
		} catch (Exception ex) {
			// This is thrown when can't even get to API (e.g. network error)!
			throw new ExternalServiceGatewayException(FORECAST_IO_SERVICE_NAME, ex);
		}
	}
}