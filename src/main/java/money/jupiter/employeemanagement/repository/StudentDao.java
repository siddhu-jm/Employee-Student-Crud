package money.jupiter.employeemanagement.repository;


import money.jupiter.employeemanagement.models.Student;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface StudentDao {

    List<Student> getStudents();

    ResponseEntity<Student> getStudentById(String id);

    ResponseEntity<String> addStudent(Student stud);

    ResponseEntity<String> deleteStudent(String id);

    ResponseEntity<String> updateStudent(Student student);


}

