package com.ram.darksky.service;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.ram.darksky.model.GoogleAPIResponse;
import com.ram.darksky.model.MapAPIResponse;
import com.ram.darksky.utils.ExternalServiceGatewayException;
import com.ram.darksky.utils.ExternalServiceInvocationException;
import com.ram.darksky.utils.LocationNotFoundException;


@Service
public class MapInfoRetrieverImpl implements MapInfoRetriever {

	protected static final String GOOGLE_MAP_GEOCODE_SERVICE = "GoogleMapAPI";
	protected static final String GOOGLE_RESPONSE_STATUS_ZERO_RESULTS = "ZERO_RESULTS";
	protected static final String GOOGLE_RESPONSE_STATUS_OK = "OK";
	protected static final String ARG_CITY = "city";
	protected static final String ARG_STATE = "state";
	protected static final String ARG_API_KEY = "apiKey";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private Environment environment;

	private String mapApiKey;

	@Value("${map.base.url}")
	private String mapBaseUrl;

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public String getMapApiKey() {
		if (mapApiKey == null) {
			this.mapApiKey = environment.getProperty("MAP_API_KEY");
		}
		return mapApiKey;
	}

	public void setMapApiKey(String mapApiKey) {
		this.mapApiKey = mapApiKey;
	}

	public String getMapBaseUrl() {
		return mapBaseUrl;
	}

	public void setMapBaseUrl(String mapBaseUrl) {
		this.mapBaseUrl = mapBaseUrl;
	}

	Map<String,String> buildURLMap(String city, String state) {
		Map<String, String> arguments = new HashMap<String,String>();
		arguments.put(ARG_CITY, city);
		arguments.put(ARG_STATE, state);
		arguments.put(ARG_API_KEY, getMapApiKey());
		
		System.out.println(arguments);
		return arguments;
	}
	
	MapAPIResponse processGoogleResults(GoogleAPIResponse rawResponse) {
		String resultStatus = rawResponse.getStatus();
		
		if (GOOGLE_RESPONSE_STATUS_OK.equals(resultStatus) && rawResponse.getResults().size() > 0) {
			return rawResponse.getResults().get(0);
		} else if (GOOGLE_RESPONSE_STATUS_ZERO_RESULTS.equals(resultStatus)) {
			throw new LocationNotFoundException();
		} else {
			// other error statuses OVER_QUERY_LIMIT, REQUEST_DENIED, INVALID_REQUEST, UNKNOWN_ERROR
			throw new ExternalServiceInvocationException(GOOGLE_MAP_GEOCODE_SERVICE, rawResponse.getStatus());
		}
	}
	
	@Override
	public MapAPIResponse getMapInfoFor(String city, String state) {
		
		GoogleAPIResponse rawResponse=null;
		MapAPIResponse mapResponse =null;
		try {
			System.out.println("Getting google map info: "+this.getMapBaseUrl());
			
			 rawResponse = restTemplate.getForObject(this.getMapBaseUrl(), 
					GoogleAPIResponse.class, buildURLMap(city, state));
			
			 mapResponse = processGoogleResults(rawResponse); 
			return mapResponse;
		} catch (HttpStatusCodeException httpStatusEx) {
			// Google usually returns errors in results structure so this isn't usually 
			// invoked but still here to convert it into our custom exception just in case
			throw new ExternalServiceInvocationException(GOOGLE_MAP_GEOCODE_SERVICE, httpStatusEx.getRawStatusCode());
		} catch (Exception ex) {
			// This is thrown when can't even get to API (e.g. network error)!
			
			throw new ExternalServiceGatewayException(GOOGLE_MAP_GEOCODE_SERVICE, ex);
			
		}
	}

}