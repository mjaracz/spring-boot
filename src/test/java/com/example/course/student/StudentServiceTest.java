package com.example.course.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	private StudentRepository studentRepository;
	private StudentService underTest;

	@BeforeEach
	void setUp() {
		underTest = new StudentService(studentRepository);
	}


	@Test
	void canGetStudents() {
		// when
		underTest.getStudents();
		// then
		verify(studentRepository).findAll();
	}

	@Test
	void addNewStudent() {
		Student student = new Student(
			"Username",
			"exmaple@gmail.com",
			LocalDate.of(2000, Month.JANUARY, 2)
		);
		underTest.addNewStudent(student);
		ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
		verify(studentRepository).save(studentArgumentCaptor.capture());
	}

	@Test
	@Disabled
	void deleteStudent() {
	}

	@Test
	@Disabled
	void updateStudent() {
	}
}