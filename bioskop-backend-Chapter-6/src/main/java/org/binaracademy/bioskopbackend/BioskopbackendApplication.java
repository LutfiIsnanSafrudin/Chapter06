package org.binaracademy.bioskopbackend;

import org.binaracademy.bioskopbackend.controller.MovieController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;

@SpringBootApplication
public class BioskopbackendApplication {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(BioskopbackendApplication.class, args);
	}

}
