package money.jupiter.employeemanagement.services.impl;

import money.jupiter.employeemanagement.repository.StudentDao;
import money.jupiter.employeemanagement.models.Student;
import money.jupiter.employeemanagement.repository.impl.StudentDaoImpl;
import money.jupiter.employeemanagement.services.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDaoImpl studentDao) {
        this.studentDao = studentDao;
    }

    public ResponseEntity<List<Student>> getStudents() {
        return (ResponseEntity<List<Student>>) ResponseEntity.ok(studentDao.getStudents());
    }

    public ResponseEntity<Student> getStudentById(String id){

        if(!studentDao.getStudents().isEmpty() && studentDao.getStudents().stream()
                .anyMatch(std -> std.getStudentId()
                        .equals(id)))
        {
            return studentDao.getStudentById(id);
        }
        else {
            return ResponseEntity.badRequest().build();
        }

    }

    public ResponseEntity<String> addStudent(Student student) {


        if(  student.getFirstName().isEmpty() || student.getLastName().isEmpty() ){
            return  ResponseEntity.badRequest().body("Enter Student details");
        }
        else{
            student.setStudentId(UUID.randomUUID().toString());
            return studentDao.addStudent(student);
        }

    }

    public ResponseEntity<String> deleteStudent(String id){

        if(studentDao.getStudents().stream()
                .anyMatch(std -> std.getStudentId()
                        .equals(id)))
        {
            return studentDao.deleteStudent(id);
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<String> updateStudent(Student student){

        if( !student.getFirstName().isEmpty() && !student.getLastName().isEmpty() && studentDao.getStudents().stream()
                .anyMatch(stud -> stud.getStudentId()
                        .equals(student.getStudentId())))
        {
            return studentDao.updateStudent(student);
        }
        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

}
