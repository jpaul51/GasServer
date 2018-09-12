package com.jonas.gas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.jonas.gas.dao")
//@EntityScan("com.jonas.gas.gasmodel")
@EntityScan("com.jonas.gas.model")
@EnableAutoConfiguration()

public class GasApplication {

	public static boolean resetGasDb=false;
	
	public static void main(String[] args) {

		for (String arg : args) {
			System.out.println("ARG: "+arg);
			if (arg.contains("resetgasdb") && arg.contains("true")) {
				System.out.println("FOUND ARG !!");;
				GasApplication.resetGasDb=true;
			}
		}

		SpringApplication.run(GasApplication.class, args);

	}

}
