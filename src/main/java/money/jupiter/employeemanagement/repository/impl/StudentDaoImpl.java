package money.jupiter.employeemanagement.repository.impl;

import money.jupiter.employeemanagement.models.EmployeeData;
import money.jupiter.employeemanagement.repository.StudentDao;
import money.jupiter.employeemanagement.models.Student;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Component
public class StudentDaoImpl implements StudentDao {

    private final HashOperations<String, String, Student> hashOperations;
    public StudentDaoImpl(RedisTemplate<String, Student> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    private static final String KEY = "Student";

    private List<Student> studentList = new ArrayList<Student>();


    @Override
    public List<Student> getStudents(){
//        List<Student> studentList1 = studentList;
        Map<String, Student> studentMap = hashOperations.entries(KEY);
        return new ArrayList<>(studentMap.values());
    }

    public ResponseEntity<Student> getStudentById(String id){

//        return ResponseEntity.ok(studentList.stream()
//                .filter(std -> std.getStudentId().equals(id))
//                .findFirst() // Get the first matched EmployeeData
//                .orElse(null));
       return ResponseEntity.ok(hashOperations.get(KEY,id));


    }

    @Override
    public ResponseEntity<String> addStudent(Student stud){

//        studentList.add(stud);
        hashOperations.put(KEY,stud.getStudentId(),stud);
        return  ResponseEntity.ok("Student added Successfully");
    }

    @Override
    public ResponseEntity<String> deleteStudent(String id) {

//        studentList = (ArrayList<Student>) studentList.stream()
//                .filter(std -> !std.getStudentId().equals(id))
//                .collect(Collectors.toList());
        hashOperations.delete(KEY, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<String> updateStudent(Student student){

//        int index= studentList.indexOf(studentList.stream()
//                .filter(stud -> stud.getStudentId().equals(stud.getStudentId()))
//                .findFirst()
//                .orElse(null));
//        studentList.set(index,student);
        hashOperations.put(KEY,student.getStudentId(),student);



        return ResponseEntity.ok("updated student successfully");
    }

}

