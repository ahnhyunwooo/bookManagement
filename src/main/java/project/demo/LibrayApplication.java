package project.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import project.demo.domain.Member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Slf4j
@SpringBootApplication
public class LibrayApplication {
	public static void main(String[] args) {
		SpringApplication.run(LibrayApplication.class, args);
	}

}
