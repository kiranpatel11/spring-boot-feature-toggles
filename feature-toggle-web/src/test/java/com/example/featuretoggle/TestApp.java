package com.example.featuretoggle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;

import com.example.featuretoggle.TestApp;

@Profile("test")
@SpringBootApplication
public class TestApp {

	public static void main(String[] args) {
		SpringApplication.run(TestApp.class, args);
	}
	
}