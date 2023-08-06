package money.jupiter.employeemanagement.services.impl;

import money.jupiter.employeemanagement.models.Student;
import money.jupiter.employeemanagement.repository.StudentRepository;
import money.jupiter.employeemanagement.services.StudentService;
import money.jupiter.employeemanagement.services.cache.CacheService;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StudentServiceImpl implements StudentService {



    private final CacheService cacheService;
    private final StudentRepository studentRepository;

    public StudentServiceImpl(CacheService cacheService, StudentRepository studentRepository) {
        this.cacheService = cacheService;
        this.studentRepository = studentRepository;
    }

    public ResponseEntity<List<Student>> getStudents() {
        try{
            return  ResponseEntity.ok().body(studentRepository.findAll());
        }
        catch (DataAccessException e){
            throw new RuntimeException("An error occurred while retrieving products",e);
        }
    }

    public ResponseEntity<Student> getStudentById(String id){

        Student student = cacheService.getStudent(id);
        if(student == null){
            try {
                Optional<Student> optionalStudent = studentRepository.findById(id);
                if (optionalStudent.isPresent()) {
                    return ResponseEntity.ok().body(optionalStudent.get());
                }
                else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
                }
            }
            catch (IllegalArgumentException e){
                throw new IllegalArgumentException("Id must not be Null",e);
            }
        }
        else {
            return ResponseEntity.ok().body(student);
        }
    }

    public ResponseEntity<String> addStudent(Student student) {

        if(  student.getFirstName().isEmpty() || student.getLastName().isEmpty() ){
            return  ResponseEntity.badRequest().body("Enter Student details");
        }
        else{
            student.setStudentId(UUID.randomUUID().toString());
            try{
                studentRepository.save(student);
                cacheService.putStudent(student);
                return ResponseEntity.ok("Student created successfully");
            }
            catch (DataIntegrityViolationException e){
                throw new IllegalArgumentException("Student Id must be unique.", e);
            }
        }
    }

    public ResponseEntity<String> deleteStudent(String id){

        if(!id.isEmpty()){
            studentRepository.deleteById(id);
            cacheService.putStudent(null);
            return ResponseEntity.noContent().build();
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public ResponseEntity<String> updateStudent(Student student){

        if( !student.getFirstName().isEmpty()
                && !student.getLastName().isEmpty()
                && studentRepository.findAll().stream()
                            .anyMatch(stud -> stud.getStudentId()
                            .equals(student.getStudentId())))
        {
            studentRepository.save(student);
            cacheService.putStudent(student);
            return ResponseEntity.ok().body("Student updated successfully");
        }
        else
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}
