package money.jupiter.employeemanagement.services;

import money.jupiter.employeemanagement.models.EmployeeData;
import money.jupiter.employeemanagement.repository.impl.EmployeeDAOImpl;
import money.jupiter.employeemanagement.services.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmployeeServiceImplTest {

    EmployeeDAOImpl employeeDAO = mock(EmployeeDAOImpl.class);
    EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeDAO);



    @Test
    public void testGetAllEmployees() {

        List<EmployeeData> mockEmployeeList = new ArrayList<>();
        mockEmployeeList.add(new EmployeeData("siddhu", "a", "sd1345"));
        mockEmployeeList.add(new EmployeeData("littu", "littu", "dfghj234"));

        doReturn(mockEmployeeList).when(employeeDAO).getAllEmployees();
        ResponseEntity<List<EmployeeData>> data =  employeeService.getAllEmployees();
        assertEquals(HttpStatus.OK, data.getStatusCode());
        List<EmployeeData> employeeList = data.getBody();
        assertNotNull(employeeList);
        assertEquals(2, employeeList.size());
        EmployeeData employee1 = employeeList.get(0);
        assertEquals("siddhu", employee1.getFirstName());
        assertEquals("a", employee1.getLastName());
        assertNotEquals("", employee1.getEmployeeId());

        EmployeeData employee2 = employeeList.get(1);
        assertEquals("littu", employee2.getFirstName());
        assertEquals("littu", employee2.getLastName());

    }



    @Test
    public void testAddEmployee_Success() {
        // Arrange
        EmployeeData emp = new EmployeeData("John", "Doe","123john");
        when(employeeDAO.postData(emp)).thenReturn(ResponseEntity.ok("Employee added successfully"));
        ResponseEntity<String> response = employeeService.addEmployee(emp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee added successfully", response.getBody());


    }

    @Test
    public void testAddEmployee_NotSuccess() {
        EmployeeData emp1 = new EmployeeData("John", "", "123john");
        ResponseEntity<String> response1 = employeeService.addEmployee(emp1);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());

        EmployeeData emp2 = new EmployeeData("", "Sam", "123john");
        ResponseEntity<String> response2 = employeeService.addEmployee(emp1);
        assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
    }

    @Test
    public void testUpdateEmployee_Success(){
        List<EmployeeData> employee=new ArrayList<>();
        EmployeeData emp=new EmployeeData("siddhu","adirinti","123sid");
        employee.add(emp);
        when(employeeDAO.getAllEmployees()).thenReturn(employee);
        when(employeeDAO.updateEmployee(emp)).thenReturn(ResponseEntity.ok("Employee updated successfully"));
        ResponseEntity<String> response = employeeService.updateEmployee(emp);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Employee updated successfully", response.getBody());

    }

    @Test
    public void testUpdateEmployee_NotSuccess(){
        EmployeeData emp=new EmployeeData("siddhu","adirinti","123sid");
        List<EmployeeData> employees = new ArrayList<>();
        when(employeeDAO.getAllEmployees()).thenReturn(employees);
        ResponseEntity<String> response = employeeService.updateEmployee(emp);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Employee with ID " + "123sid" + " not found", response.getBody());

        EmployeeData emp1=new EmployeeData("","","123siddd");
        List<EmployeeData> employees1 = new ArrayList<>();
        employees1.add(emp1);
        when(employeeDAO.getAllEmployees()).thenReturn(employees1);
        ResponseEntity<String> response1 = employeeService.updateEmployee(emp1);
        assertEquals(HttpStatus.BAD_REQUEST, response1.getStatusCode());
        assertEquals("Enter employee details", response1.getBody());
    }

    @Test
    public void testDeleteStudent_Success() {
        List<EmployeeData> mockEmployeeList = new ArrayList<>();
        mockEmployeeList.add(new EmployeeData("Harsha", "Vardhan",  "098765"));
        mockEmployeeList.add(new EmployeeData("siddhu", "adirinti",  "ertyui"));
        String idToDelete = "098765";
        doReturn(mockEmployeeList).when(employeeDAO).getAllEmployees();
        doReturn(ResponseEntity.status(HttpStatus.OK).build()).when(employeeDAO).dropEmployee(idToDelete);
        ResponseEntity<String> response = employeeService.dropEmployee(idToDelete);
        assertEquals(HttpStatus.OK,response.getStatusCode());
    }

    @Test
    public void testDeleteStudent_NotSuccess() {
        List<EmployeeData> mockEmployeeList = new ArrayList<>();
        mockEmployeeList.add(new EmployeeData("Harsha", "Vardhan",  "098765"));
        mockEmployeeList.add(new EmployeeData("siddhu", "adirinti",  "ertyui"));
        String idToDelete = "12345";
        doReturn(mockEmployeeList).when(employeeDAO).getAllEmployees();
        ResponseEntity<String> response = employeeService.dropEmployee(idToDelete);
        assertEquals(HttpStatus.BAD_REQUEST,response.getStatusCode());
        String idToDelete1 = "";
        doReturn(mockEmployeeList).when(employeeDAO).getAllEmployees();
        ResponseEntity<String> response1 = employeeService.dropEmployee(idToDelete1);
        assertEquals(HttpStatus.BAD_REQUEST,response1.getStatusCode());
    }

}








