package com.ram.darksky.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties
@ComponentScan({"com.ram.darksky.controller,com.ram.darksky.model,com.ram.darksky.service,utils,com.ram.darksky.test"})
public class DarkskyApplicationStarter {

	public static void main(String[] args) {
		SpringApplication.run(DarkskyApplicationStarter.class, args);

		
	}

}
