package com.ram.darksky.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ram.darksky.model.ForeCastResponse;
import com.ram.darksky.starter.DarkskyApplicationStarter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(classes=DarkskyApplicationStarter.class)
public class DarkSkyWeatherForeCastControllerTest {

	@Test
	public void getForeCastResponseTest(){
	 assertEquals(1,1);
	 
	 RestTemplate restTemplate = new RestTemplate();
     
	    final String baseUrl = "http://localhost:8080/getforecastData/Campbell,CA";
	    URI uri;
		try {
			uri = new URI(baseUrl);
			 ResponseEntity<ForeCastResponse> result = restTemplate.getForEntity(uri, ForeCastResponse.class);
		     
			    //Verify request succeed
			   assertEquals(200, result.getStatusCodeValue());
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
	   
	}
}
