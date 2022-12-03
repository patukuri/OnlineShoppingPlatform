package com.securecoding.onelineshoppingplatform;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
//@ComponentScan("com.securecoding.onlineshoppingplatform")

public class OnlineShoppingPlatformApplication {

	public static void main(String[] args) {
		SpringApplication.run(OnlineShoppingPlatformApplication.class, args);
	}

}
