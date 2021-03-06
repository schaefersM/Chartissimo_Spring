package com.schaefersm.gateway;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;


@Configuration
@EnableWebFlux
public class CorsConfig implements WebFluxConfigurer {

   @Override
   public void addCorsMappings(CorsRegistry registry) {
      registry.addMapping("/**")
            .allowCredentials(true)
            .allowedOrigins("http://localhost:3000")
            .allowedHeaders("*")
            .allowedMethods("*");
   }

   @Bean
   public CorsWebFilter corsWebFilter() {
      CorsConfiguration corsConfiguration = new CorsConfiguration();
      corsConfiguration.setAllowCredentials(true);
      corsConfiguration.addAllowedHeader("*");
      corsConfiguration.addAllowedMethod("*");
      corsConfiguration.addAllowedOrigin("http://localhost:3000");
      corsConfiguration.addAllowedOrigin("http://localhost");
      UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
      corsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
      return new CorsWebFilter(corsConfigurationSource);
   }
}