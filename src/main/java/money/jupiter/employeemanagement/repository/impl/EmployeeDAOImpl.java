package money.jupiter.employeemanagement.repository.impl;

import money.jupiter.employeemanagement.models.Employee;
import money.jupiter.employeemanagement.repository.EmployeeDAO;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    List<Employee> employeeList= new ArrayList<>();
    @Override
    public List<Employee> getAllEmployees(){
        return employeeList;
    }
    @Override
    public Employee getEmployeeById(String employeeId){
        return employeeList.stream()
                .filter(emp -> emp.getEmployeeId().equals(employeeId))
                .findFirst() // Get the first matched EmployeeData
                .orElse(null);
    }


    @Override
    public ResponseEntity<String> postData(Employee emp) {
        employeeList.add(emp);
        return ResponseEntity.ok("employee added successfully");
    }
    @Override
    public ResponseEntity<String> dropEmployee(String employeeId){
        employeeList = (List<Employee>) employeeList.stream().filter(i-> !employeeId.equals(i.getEmployeeId())).collect(Collectors.toList());
        return ResponseEntity.ok("Deleted Employee Sucsessfully");
    }
    @Override
    public ResponseEntity<String> updateEmployee(Employee emp){
        int index=employeeList.indexOf(employeeList.stream()
                .filter(employee -> employee.getEmployeeId().equals(emp.getEmployeeId()))
                .findFirst()
                .orElse(null));
        employeeList.set(index,emp);
        return  ResponseEntity.ok("updated employee successfully");
    }
}
