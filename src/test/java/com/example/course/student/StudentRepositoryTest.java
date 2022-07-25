package com.example.course.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class StudentRepositoryTest {

	@Autowired
	private StudentRepository underTest;

	@AfterEach
	void tearDown() {
		this.underTest.deleteAll();
	}

	@Test
	void itShouldCheckIfEmailExist() {
		String email = "exmaple@gmail.com";
		Student student = new Student(
			"Jamila",
			email,
			LocalDate.of(2000, Month.JANUARY, 2)
		);
		underTest.save(student);
		boolean exist = underTest.checkStudentByEmail(email);
		assertThat(exist).isTrue();
	}

	@Test
	void itShouldCheckIfEmailNotExist() {
		String email = "emailNotExist@gmail.com";
		boolean exist = underTest.checkStudentByEmail(email);
		assertThat(exist).isFalse();
	}
}