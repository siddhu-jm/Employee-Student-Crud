package money.jupiter.employeemanagement.services.impl;

import money.jupiter.employeemanagement.models.EmployeeData;
import money.jupiter.employeemanagement.repository.impl.EmployeeDAOImpl;
import money.jupiter.employeemanagement.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class EmployeeServiceImpl implements EmployeeService {
    public final EmployeeDAOImpl dataAccessObject;

    public EmployeeServiceImpl(EmployeeDAOImpl dataAccessObject) {
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public ResponseEntity<List<EmployeeData>> getAllEmployees(){
        return ResponseEntity.ok(dataAccessObject.getAllEmployees());
    }
    @Override
    public ResponseEntity<EmployeeData> getEmployeeById(String employeeId){
        EmployeeData emp=dataAccessObject.getEmployeeById(employeeId);
         if(emp!=null) ResponseEntity.ok(emp);
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
