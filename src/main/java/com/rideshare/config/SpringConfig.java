package com.rideshare.config;

import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.rideshare.services.MapBuilder;
import com.rideshare.services.RouteFinder;

@Configuration
@EnableWebMvc
@ComponentScan("com.rideshare")
public class SpringConfig {
	
	@Autowired
	private MapBuilder mapBuilder;

	@Bean
	public RouteFinder createRouteFinder() {
		return new RouteFinder(mapBuilder.readMapObject());
	}

}
