package com.zr.rail;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.zr.rail.dao")
public class RailApplication {

	public static void main(String[] args) {
		SpringApplication.run(RailApplication.class, args);
	}
}
