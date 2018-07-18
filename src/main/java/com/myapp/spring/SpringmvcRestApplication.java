package com.myapp.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;
import org.springframework.web.servlet.view.tiles3.TilesViewResolver;

@SpringBootApplication
public class SpringmvcRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringmvcRestApplication.class, args);
	}
	   
	@Bean
	public ViewResolver viewResolver() {
		final TilesViewResolver resolver = new TilesViewResolver();
     resolver.setViewClass(TilesView.class);
     return resolver;
	   }

	   /**
	    * Configures Tiles at application startup.
	    */
	   @Bean
	   public TilesConfigurer tilesConfigurer() {
	      TilesConfigurer configurer = new TilesConfigurer();
	      configurer.setDefinitions(new String[] { "/WEB-INF/jsp/tiles.xml" });
	      configurer.setCheckRefresh(true);
	      return configurer;
	   }
}


