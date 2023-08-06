package money.jupiter.employeemanagement.controllers;

import money.jupiter.employeemanagement.models.Employee;
import money.jupiter.employeemanagement.services.impl.EmployeeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")

public class EmployeeController {

    private final EmployeeServiceImpl service;

    public EmployeeController(EmployeeServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {

        return service.getAllEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable String employeeId){
        return service.getEmployeeById(employeeId);
    }

    @PostMapping("/employees")
    public  ResponseEntity<String> addEmployee(@RequestBody Employee emp){
        return service.addEmployee(emp);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> dropEmployee(@PathVariable String employeeId){
        return service.dropEmployee(employeeId);
    }

    @PutMapping("/employees")
    public ResponseEntity<String> updateEmployee(@RequestBody Employee emp){
        return service.updateEmployee(emp);
    }
}
