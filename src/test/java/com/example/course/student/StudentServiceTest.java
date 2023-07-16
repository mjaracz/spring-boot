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
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
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
		underTest.getStudents();
		verify(studentRepository).findAll();
	}

	@Test
	void canAddNewStudent() {
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
	void whenAddNewStudentAndEmailIsTakenThrowException() {
		Student student = new Student(
			"Username",
			"noCorrectEmail@example.com",
			LocalDate.of(2000, Month.JANUARY, 4)
		);
		given(studentRepository.checkStudentByEmail(student.getEmail()))
			.willReturn(true);
		assertThatThrownBy(() -> underTest.addNewStudent(student))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("email: " + student.getEmail() + " is taken");
		verify(studentRepository, never()).save(any());
	}

	@Test
	void whenDeleteStudentAndIdNotExistThrowException() {
		Long deletedStudentId = 2L;
		given(studentRepository.existsById(deletedStudentId))
				.willReturn(false);
		assertThatThrownBy(() -> underTest.deleteStudent(deletedStudentId))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("student with id " + deletedStudentId + " dose not exist");
		verify(studentRepository, never()).deleteById(any());
	}

	@Test
	@Disabled
	void shouldUpdateStudent() {
		Optional<Student> student = studentRepository.findById(2L);

		underTest.updateStudent(2L, "updated name", "update-me@java-advantger.pl");
		ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);

	}
}