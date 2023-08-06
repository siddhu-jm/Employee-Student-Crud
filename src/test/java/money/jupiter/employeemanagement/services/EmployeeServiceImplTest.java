package money.jupiter.employeemanagement.services;

import money.jupiter.employeemanagement.models.Employee;
import money.jupiter.employeemanagement.models.Student;
import money.jupiter.employeemanagement.repository.EmployeeRepository;
import money.jupiter.employeemanagement.services.cache.CacheService;
import money.jupiter.employeemanagement.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    CacheService cacheService = mock(CacheService.class);
    EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
    EmployeeServiceImpl employeeService = new EmployeeServiceImpl( employeeRepository, cacheService);



    @Test
    public void testGetAllEmployees() {

        List<Employee> mockEmployeeList = new ArrayList<>();
        mockEmployeeList.add(new Employee("siddhu", "a", "sd1345"));
        mockEmployeeList.add(new Employee("littu", "littu", "dfghj234"));

        doReturn(mockEmployeeList).when(employeeRepository).findAll();
        ResponseEntity<List<Employee>> data =  employeeService.getAllEmployees();
        assertEquals(HttpStatus.OK, data.getStatusCode());
        List<Employee> employeeList = data.getBody();
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());

        Employee employee1 = employeeList.get(0);
        assertEquals("siddhu", employee1.getFirstName());
        assertEquals("a", employee1.getLastName());
        assertNotEquals("", employee1.getEmployeeId());

        Employee employee2 = employeeList.get(1);
        assertEquals("littu", employee2.getFirstName());
        assertEquals("littu", employee2.getLastName());

    }

    @Test
    public void testGetEmployeeById_Success() {

        Optional<Employee> mockEmployee = Optional.of(new Employee("Harsha", "Vardhan", "dfghj-09876"));
        doReturn(mockEmployee).when(employeeRepository).findById("dfghj-09876");
        ResponseEntity<Employee> data = employeeService.getEmployeeById("dfghj-09876");
        Employee employee = data.getBody();
        assertEquals("dfghj-09876",employee.getEmployeeId());
    }

    @Test
    public void testGetEmployeeById_NotSuccess() {

        Employee employee = null;
        String id = "12345";
        Optional<Employee> mockEmployee = Optional.ofNullable(employee);
        doReturn(null).when(cacheService).getEmployee(id);
        doReturn(mockEmployee).when(employeeRepository).findById(id);
        ResponseEntity<Employee> response = employeeService.getEmployeeById(id);
        assertEquals(HttpStatus.NOT_FOUND,response.getStatusCode());
    }


    @Test
    public void testAddEmployee_Success() {
        // Arrange
        Employee emp = new Employee("John", "Doe","123john");
        when(employeeRepository.save(emp)).thenReturn(emp);
        ResponseEntity<String> response = employeeService.addEmployee(emp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee added successfully", response.getBody());
    }

    @Test
    public void testAddEmployee_NotSuccess() {
        Employee emp1 = new Employee("John", "", "123john");
        ResponseEntity<String> response1 = employeeService.addEmployee(emp1);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        Employee emp2 = new Employee("", "Sam", "123john");
        ResponseEntity<String> response2 = employeeService.addEmployee(emp1);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    public void testUpdateEmployee_Success(){
        List<Employee> employeeList = new ArrayList<>();
        Employee emp=new Employee("siddhu","adirinti","123sid");
        employeeList.add(emp);
        when(employeeRepository.findAll()).thenReturn(employeeList);
        when(employeeRepository.save(emp)).thenReturn(emp);
        ResponseEntity<String> response = employeeService.updateEmployee(emp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee updated successfully", response.getBody());
    }

    @Test
    public void testUpdateEmployee_NotSuccess(){
        Employee emp=new Employee("siddhu","adirinti","123sid");
        List<Employee> employees = new ArrayList<>();
        when(employeeRepository.findAll()).thenReturn(employees);
        ResponseEntity<String> response = employeeService.updateEmployee(emp);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Employee with ID " + "123sid" + " not found", response.getBody());

        Employee emp1=new Employee("","","123siddd");
        List<Employee> employees1 = new ArrayList<>();
        employees1.add(emp1);
        when(employeeRepository.findAll()).thenReturn(employees1);
        ResponseEntity<String> response1 = employeeService.updateEmployee(emp1);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals("Enter employee details", response1.getBody());
    }

    @Test
    public void testDeleteEmployee_Success() {

        String idToDelete = "098765";
        ResponseEntity<String> response = employeeService.dropEmployee(idToDelete);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());
    }

    @Test
    public void testDeleteEmployee_NotSuccess() {

        String idToDelete = "12345";
        ResponseEntity<String> response = employeeService.dropEmployee(idToDelete);
        assertEquals(HttpStatus.NO_CONTENT,response.getStatusCode());

        String idToDelete1 = "";
        ResponseEntity<String> response1 = employeeService.dropEmployee(idToDelete1);
        assertEquals(HttpStatus.BAD_REQUEST,response1.getStatusCode());
    }

}








