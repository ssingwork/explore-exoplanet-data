package com.explore;

import com.explore.services.DataManager;
import com.explore.services.impl.DataManagerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class DataExoplanetApplication {

  @Autowired
  Environment environment;

  public static void main(String[] args) {
    SpringApplication.run(DataExoplanetApplication.class, args);
  }

  @Bean
  public DataManager dataManager(){
    return  new DataManagerImpl(environment);
  }

}
