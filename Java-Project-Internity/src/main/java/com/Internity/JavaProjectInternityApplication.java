package com.Internity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.Internity.controller")
public class JavaProjectInternityApplication {
	public static void main(String[] args) {
		SpringApplication.run(JavaProjectInternityApplication.class, args);
	}

}
