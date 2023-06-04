package com.webexecuter.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
public class ServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}

}
