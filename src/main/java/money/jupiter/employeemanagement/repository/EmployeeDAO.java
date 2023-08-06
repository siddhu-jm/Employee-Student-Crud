package money.jupiter.employeemanagement.repository;
import money.jupiter.employeemanagement.models.Employee;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeDAO {
    Employee getEmployeeById(String employeeId);
    List<Employee> getAllEmployees();
    ResponseEntity<String> postData(Employee emp);
    ResponseEntity<String> dropEmployee(String employeeId);
    ResponseEntity<String> updateEmployee(Employee emp);
}
