package Fabio.Reis.Scholarship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@EnableJpaRepositories(basePackages = "Fabio.Reis.Scholarship.repository")
@SpringBootApplication
public class ScholarshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScholarshipApplication.class, args);
	}

}
