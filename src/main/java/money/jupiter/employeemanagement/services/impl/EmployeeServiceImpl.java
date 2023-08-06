package money.jupiter.employeemanagement.services.impl;

import money.jupiter.employeemanagement.models.Employee;
import money.jupiter.employeemanagement.repository.EmployeeRepository;
import money.jupiter.employeemanagement.services.EmployeeService;
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
public class EmployeeServiceImpl implements EmployeeService {
    //private final EmployeeDAOImpl dataAccessObject;

    private final EmployeeRepository employeeRepository;
    private final CacheService cacheService;
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, CacheService cacheService) {
        this.employeeRepository = employeeRepository;
        this.cacheService = cacheService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees(){
        try {
            return ResponseEntity.ok(employeeRepository.findAll());
        }
        catch (DataAccessException e){
            throw new RuntimeException("An error occurred while retrieving products");
        }

    }
    @Override
    public ResponseEntity<Employee> getEmployeeById(String employeeId){

        Employee employee = cacheService.getEmployee(employeeId);
        if (employee == null){
            Optional<Employee> emp = employeeRepository.findById(employeeId);
            if(emp.isPresent() )  ResponseEntity.ok(emp.get());
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(employee);
        }
    }

    @Override
    public ResponseEntity<String> addEmployee(Employee emp){
        if(emp.getFirstName().isEmpty() || emp.getLastName().isEmpty()){
           return  ResponseEntity.badRequest().body("Enter employee details");
        }
        else {
            emp.setEmployeeId(UUID.randomUUID().toString());
            try{
                Employee savedEmployee = employeeRepository.save(emp);
                cacheService.putEmployee(emp);
                return ResponseEntity.ok("Employee added successfully");
            }
            catch (DataIntegrityViolationException e){
                throw new IllegalArgumentException("Student Id must be unique",e);
            }
        }
    }
    @Override
    public ResponseEntity<String> dropEmployee(String employeeId){
        if(!employeeId.isEmpty()) {
            cacheService.putEmployee(null);
            employeeRepository.deleteById(employeeId);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @Override
    public ResponseEntity<String> updateEmployee(Employee emp){
        if(emp.getFirstName().isEmpty() || emp.getLastName().isEmpty()){
            return  ResponseEntity.badRequest().body("Enter employee details");
        }
        else {
            if (employeeRepository.findAll().stream()
                    .anyMatch(i -> i.getEmployeeId().equals(emp.getEmployeeId())))
            {
                employeeRepository.save(emp);
                cacheService.putEmployee(emp);
                return ResponseEntity.ok("Employee updated successfully");
            }
            return ResponseEntity.badRequest().body("Employee with ID " + emp.getEmployeeId() + " not found");
        }
    }
}
