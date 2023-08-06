package money.jupiter.employeemanagement.services;

import money.jupiter.employeemanagement.models.Student;
import money.jupiter.employeemanagement.repository.StudentRepository;
import money.jupiter.employeemanagement.services.cache.CacheService;
import money.jupiter.employeemanagement.services.impl.StudentServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class StudentServiceImplTest {


    CacheService cacheService = mock(CacheService.class);
    StudentRepository studentRepository = mock(StudentRepository.class);
    StudentServiceImpl studentService = new StudentServiceImpl(cacheService, studentRepository);

    @Test
    public void testGetAllEmployees() {
        List<Student> mockStudentList = new ArrayList<>();

        mockStudentList.add(new Student("Harsha", "Vardhan", 1, "098765"));
        mockStudentList.add(new Student("siddhu", "a", 2, "ertyui"));

        doReturn(mockStudentList).when(studentRepository).findAll();
        ResponseEntity<List<Student>> data =  studentService.getStudents();
        assertEquals(HttpStatus.OK, data.getStatusCode());

        List<Student> studentList = data.getBody();
        assertNotNull(studentList);
        assertEquals(2, studentList.size());

        Student student1 = studentList.get(0);
        assertNotEquals("",student1.getStudentId());
        assertEquals("Harsha", student1.getFirstName());
        assertEquals("Vardhan", student1.getLastName());
        assertEquals(1, student1.getStd());

        Student student2 = studentList.get(1);
        assertNotEquals("",student2.getStudentId());
        assertEquals("siddhu", student2.getFirstName());
        assertEquals("a", student2.getLastName());
        assertEquals(2, student2.getStd());
    }

//    @Test
//    public void testGetStudentById() {
//
//        ResponseEntity<Student> mockStudent = new ResponseEntity<Student>(new Student("Harsha", "Vardhan", 1, "dfghj-09876"),HttpStatus.OK);
//
//        doReturn(mockStudent).when(studentDao).getStudentById("dfghj-09876");
//        ResponseEntity<Student> data = studentService.getStudentById("dfghj-09876");
//        Student student = data.getBody();
//        assertEquals("dfghj-09876",student.getStudentId());
//    }

    @Test
    public void testAddStudent_Success(){

        Student mockStudent = new Student("Harsha", "Vardhan", 1, "098765");

        doReturn(mockStudent).when(studentRepository).save(mockStudent);
        ResponseEntity<String> response = studentService.addStudent(mockStudent);
        assertEquals(HttpStatus.OK,response.getStatusCode());

    }

    @Test
    public void testAddStudent_NotSuccess(){

        Student mockStudent = new Student("", "Vardhan", 1, "098765");
        doReturn(ResponseEntity.ok("Student added successfully")).when(studentRepository).save(mockStudent);
        ResponseEntity<String> response = studentService.addStudent(mockStudent);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());

        Student mockStudent1 = new Student("Harsha", "", 1, "098765");
        doReturn(ResponseEntity.ok("Student added successfully")).when(studentRepository).save(mockStudent1);
        ResponseEntity<String> response1 = studentService.addStudent(mockStudent1);
        assertEquals(HttpStatus.BAD_REQUEST,response1.getStatusCode());


    }

    @Test
    public void testDeleteStudent_Success() {

        String idToDelete = "098765";

        ResponseEntity<String> response = studentService.deleteStudent(idToDelete);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

    }

    @Test
    public void testDeleteStudent_NotSuccess() {

        String idToDelete = "12345";

        ResponseEntity<String> response = studentService.deleteStudent(idToDelete);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

        String idToDelete1 = "";
        ResponseEntity<String> response1 = studentService.deleteStudent(idToDelete1);
        assertEquals(HttpStatus.BAD_REQUEST,response1.getStatusCode());


    }

    @Test
    public void testUpdateStudent_Success(){

        List<Student> mockStudentList = new ArrayList<>();
        Student student = new Student("Harsha","Vardhan",1,"098765");
        mockStudentList.add(student);
        when(studentRepository.findAll()).thenReturn(mockStudentList);
        when(studentRepository.save(student)).thenReturn(student);
        ResponseEntity<String> response = studentService.updateStudent(student);
        assertEquals(HttpStatus.OK, response.getStatusCode());

    }
    @Test

    public void testUpdateStudent_NotSuccess(){

        Student student = new Student("Harsha","",1,"098765");

        List<Student> mockStudentList = new ArrayList<>();
        mockStudentList.add(student);
        when(studentRepository.findAll()).thenReturn(mockStudentList);
        ResponseEntity<String> response = studentService.updateStudent(student);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        Student student1 = new Student("","",2,"098765");
        List<Student> mockStudentList1 = new ArrayList<>();
        mockStudentList1.add(student1);
        when(studentRepository.findAll()).thenReturn(mockStudentList1);
        ResponseEntity<String> response1 = studentService.updateStudent(student1);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

    }


}









