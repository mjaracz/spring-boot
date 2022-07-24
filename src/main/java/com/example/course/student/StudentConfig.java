package com.example.course.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

	@Bean
	CommandLineRunner commandLineRunner(StudentRepository studentRepository) {
		return args -> {
			Student student = new Student(
				"Name",
				"email@domain.com",
				LocalDate.of(2000, Month.JANUARY, 5)
			);

			Student kazimierz = new Student(
				"Kazimierz",
				"kazimierz@website.com",
				LocalDate.of(2002, Month.FEBRUARY, 5)
			);
			studentRepository.saveAll(
				List.of(student, kazimierz)
			);
		};
	}
}
