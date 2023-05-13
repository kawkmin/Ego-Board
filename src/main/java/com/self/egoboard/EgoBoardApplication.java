package com.self.egoboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EgoBoardApplication {

  public static void main(String[] args) {
    SpringApplication.run(EgoBoardApplication.class, args);
  }

}
