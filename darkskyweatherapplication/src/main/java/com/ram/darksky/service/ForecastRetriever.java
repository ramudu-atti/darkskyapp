package com.ram.darksky.service;

import com.ram.darksky.model.ForeCastResponse;

public interface ForecastRetriever {

	/**
	 * Get the ForeCast response specific to latitude and longitude
	 * @param longitude
	 * @param latitude
	 * @return
	 */
	public ForeCastResponse getForcastFor(String longitude, String latitude);
	
}