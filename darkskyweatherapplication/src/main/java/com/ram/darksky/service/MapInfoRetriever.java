package com.ram.darksky.service;

import com.ram.darksky.model.MapAPIResponse;

public interface MapInfoRetriever {

	public MapAPIResponse getMapInfoFor(String city, String state);
	
}