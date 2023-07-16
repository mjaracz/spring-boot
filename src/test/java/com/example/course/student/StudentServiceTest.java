package com.example.course.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

	@Mock
	private StudentRepository studentRepositoryMock;
	private StudentService studentService;

	@BeforeEach
	void setUp() {
		studentService = new StudentService(studentRepositoryMock);

	}


	@Test
	void canGetStudents() {
		studentService.getStudents();
		verify(studentRepositoryMock).findAll();
	}

	@Test
	void canAddNewStudent() {
		Student student = new Student(
			"Username",
			"exmaple@gmail.com",
			LocalDate.of(2000, Month.JANUARY, 2)
		);
		studentService.addNewStudent(student);
		ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
		verify(studentRepositoryMock).save(studentArgumentCaptor.capture());
	}

	@Test
	void whenAddNewStudentAndEmailIsTakenThrowException() {
		Student student = new Student(
			"Username",
			"noCorrectEmail@example.com",
			LocalDate.of(2000, Month.JANUARY, 4)
		);
		given(studentRepositoryMock.checkStudentByEmail(student.getEmail()))
			.willReturn(true);
		assertThatThrownBy(() -> studentService.addNewStudent(student))
			.isInstanceOf(IllegalStateException.class)
			.hasMessageContaining("email: " + student.getEmail() + " is taken");
		verify(studentRepositoryMock, never()).save(any());
	}

	@Test
	void whenDeleteStudentAndIdNotExistThrowException() {
		Long deletedStudentId = 2L;
		given(studentRepositoryMock.existsById(deletedStudentId))
				.willReturn(false);
		assertThatThrownBy(() -> studentService.deleteStudent(deletedStudentId))
				.isInstanceOf(IllegalStateException.class)
				.hasMessageContaining("student with id " + deletedStudentId + " dose not exist");
		verify(studentRepositoryMock, never()).deleteById(any());
	}


	@Test
	void shouldUpdateStudent() {
		Student mockStudentData = new Student(2L, "defaultMockName", "email@example.com", LocalDate.now());
		when(studentRepositoryMock.findById(2L)).thenReturn(Optional.of(mockStudentData));

		Student updatedStudent = studentService.updateStudent(2L, "updated-name", "updated@email.com");
		assertThat(updatedStudent.equals(mockStudentData)).isTrue();
	}
}