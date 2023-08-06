package money.jupiter.employeemanagement.services;

import money.jupiter.employeemanagement.models.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;
public interface EmployeeService {
    ResponseEntity<Employee> getEmployeeById(String employeeId);
    ResponseEntity<List<Employee>> getAllEmployees();
    ResponseEntity<String> addEmployee(Employee emp);
    ResponseEntity<String> dropEmployee(String employeeId);
    ResponseEntity<String> updateEmployee(Employee emp);


}
