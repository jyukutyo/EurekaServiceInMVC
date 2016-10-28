package com.jyukutyo;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@RestController
@EnableEurekaClient
public class ServiceApplication {

	private static final Logger logger = LoggerFactory.getLogger(ServiceApplication.class);

	/**
	 * curl -v -X GET http://localhost:8080/jyukutyo
	 */
	@RequestMapping(value = "{name}", method = RequestMethod.GET)
	ResponseEntity<?> get(@PathVariable String name) {
		logger.info(new Date().toString());
		return new ResponseEntity<>(name, HttpStatus.OK);
	}

    public static void main(String[] args) {
        new SpringApplicationBuilder(ServiceApplication.class).run(args);
    }


}
