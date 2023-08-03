package springboot.mongodb.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class SpringbootMongodbLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongodbLoginApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository){
		return args -> {
			Address address = new Address("England","London", "NE9");
			Student student = new Student(
					"Jamila",
					"Ahmed",
					"test@gmail.com",
					Gender.FEMALE,
					address,
					List.of("Computer Science"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);
			repository.insert(student);
		};
	}
}
