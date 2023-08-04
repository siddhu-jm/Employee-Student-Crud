package money.jupiter.employeemanagement.controllers;

import money.jupiter.employeemanagement.models.EmployeeData;
import money.jupiter.employeemanagement.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/api")

public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeData>> getAllEmployees() {

        return service.getAllEmployees();
    }

    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeData> getEmployeeById(@PathVariable String employeeId){
        return service.getEmployeeById(employeeId);
    }

    @PostMapping("/employees")
    public  ResponseEntity<String> addEmployee(@RequestBody EmployeeData emp){
        return service.addEmployee(emp);
    }

    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity<String> dropEmployee(@PathVariable String employeeId){
        return service.dropEmployee(employeeId);
    }

    @PutMapping("/employees")
    public ResponseEntity<String> updateEmployee(@RequestBody EmployeeData emp){
        return service.updateEmployee(emp);
    }
}
