package com.example.course.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

	private final StudentRepository studentRepository;

	@Autowired
	public StudentService(StudentRepository studentRepository) {
		this.studentRepository = studentRepository;
	}

	public List<Student> getStudents() {
		return studentRepository.findAll();
	}

	public Student addNewStudent(Student student) {
		boolean exist = studentRepository.checkStudentByEmail(student.getEmail());

		if (exist) {
			throw new IllegalStateException("email: " + student.getEmail() + " is taken");
		}
		return studentRepository.save(student);
	}

	public void deleteStudent(Long studentId) {
		boolean exists = studentRepository.existsById(studentId);
		if (!exists) {
			throw new IllegalStateException("student with id " + studentId + " dose not exist");
		}
		studentRepository.deleteById(studentId);
	}

	@Transactional
	public Student updateStudent(Long id, String name, String email) {
		Student student = studentRepository.findById(id).orElseThrow(() -> new IllegalStateException("id not exist"));
		if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) student.setName(name);
		if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) student.setName(email);
		return student;
	}
}
