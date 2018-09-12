package com.jonas.gas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("com.jonas.gas.config")
public class GasProperties
{

	public String geocodingIp = "http://localhost:2322/api";
	
	
}
