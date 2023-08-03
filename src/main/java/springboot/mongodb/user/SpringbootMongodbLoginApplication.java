package springboot.mongodb.user;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Queue;

@SpringBootApplication
public class SpringbootMongodbLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootMongodbLoginApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){
		return args -> {
			String email = "test123@gmail.com";
			Address address = new Address("England","London", "NE9");
			Student student = new Student(
					"test",
					"test",
					email,
					Gender.FEMALE,
					address,
					List.of("Mathematics"),
					BigDecimal.TEN,
					LocalDateTime.now()
			);
			//usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);
			repository.findStudentByEmail(email)
					.ifPresentOrElse(s -> {
						System.out.println(student + " Alrerady exists");
					},()->{
						System.out.println("Inserting Student:"+ student);
						repository.insert(student);
					});

		};
	}

	private static void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<Student> students = mongoTemplate.find(query, Student.class);
		if(students.size() > 1){
			throw new IllegalStateException("Found students with duplicate email id:" + email);
		}
		if(students.isEmpty()){
			System.out.println("Inserting Student:"+ student);
			repository.insert(student);
		} else {
			System.out.println(student + " Alrerady exists");
		}
	}
}
