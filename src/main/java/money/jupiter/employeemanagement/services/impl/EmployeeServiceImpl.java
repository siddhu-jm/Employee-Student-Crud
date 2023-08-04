package money.jupiter.employeemanagement.services.impl;

import money.jupiter.employeemanagement.models.EmployeeData;
import money.jupiter.employeemanagement.repository.EmployeeDAO;
import money.jupiter.employeemanagement.services.EmployeeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.List;
import java.util.UUID;


@Service
@EnableCaching
public class EmployeeServiceImpl implements EmployeeService {
    public final EmployeeDAO dataAccessObject;

    public EmployeeServiceImpl(EmployeeDAO dataAccessObject) {
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public ResponseEntity<List<EmployeeData>> getAllEmployees(){
        return ResponseEntity.ok(dataAccessObject.getAllEmployees());
    }
    @Override
    @Cacheable(key ="#employeeId",value = "Employee")
    public ResponseEntity<EmployeeData> getEmployeeById(String employeeId){
        if (dataAccessObject.getAllEmployees().stream().anyMatch(i -> i.getEmployeeId().equals(employeeId))) return ResponseEntity.ok(dataAccessObject.getEmployeeById(employeeId));
        return ResponseEntity.badRequest().build();


    }

    @Override
    public ResponseEntity<String> addEmployee(EmployeeData emp){
        if(emp.getFirstName().isEmpty() || emp.getLastName().isEmpty()){
           return  ResponseEntity.badRequest().body("Enter employee details");
        }
        else {
            emp.setEmployeeId(UUID.randomUUID().toString());
            return dataAccessObject.postData(emp);
        }
    }
    @Override
    public ResponseEntity<String> dropEmployee(String employeeId){
        if(dataAccessObject.getAllEmployees().stream().anyMatch(i->i.getEmployeeId().equals(employeeId))) return dataAccessObject.dropEmployee(employeeId);
        return ResponseEntity.badRequest().body("Employee with ID " + employeeId + " not found");

    }
    @Override
    public ResponseEntity<String> updateEmployee(EmployeeData emp){
        if(emp.getFirstName().isEmpty() || emp.getLastName().isEmpty()){
            return  ResponseEntity.badRequest().body("Enter employee details");
        }
        else {
            if (dataAccessObject.getAllEmployees().stream().anyMatch(i -> i.getEmployeeId().equals(emp.getEmployeeId())))
                return dataAccessObject.updateEmployee(emp);
            return ResponseEntity.badRequest().body("Employee with ID " + emp.getEmployeeId() + " not found");
        }
    }
}
