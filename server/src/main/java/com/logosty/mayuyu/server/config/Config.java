package com.logosty.mayuyu.server.config;

import java.time.Duration;
import java.util.Arrays;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author logosty(ganyingle) on 2020/1/15 14:24
 */
@Configuration
public class Config {

  @Bean
  public RestTemplate restTemplate() {
    RestTemplate restTemplate = new RestTemplateBuilder()
        .setConnectTimeout(Duration.ofMillis(100000))
        .setReadTimeout(Duration.ofMillis(100000))
        .build();

    restTemplate.setMessageConverters(Arrays.asList(
        new MappingJackson2HttpMessageConverter())
    );
    return restTemplate;
  }

}
