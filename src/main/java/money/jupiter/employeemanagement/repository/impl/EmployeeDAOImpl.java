package money.jupiter.employeemanagement.repository.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import money.jupiter.employeemanagement.models.EmployeeData;
import money.jupiter.employeemanagement.models.Student;
import money.jupiter.employeemanagement.repository.EmployeeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class EmployeeDAOImpl implements EmployeeDAO {
    private final HashOperations<String, String, EmployeeData> hashOperations;
    public EmployeeDAOImpl(RedisTemplate<String,EmployeeData> redisTemplate1) {
        this.hashOperations = redisTemplate1.opsForHash();
    }

    private static final String KEY = "Employee";
    @Override
    public List<EmployeeData> getAllEmployees(){
        Map<String, EmployeeData> employeeMap = hashOperations.entries(KEY);
        return new ArrayList<>(employeeMap.values());
    }
    @Override
    public EmployeeData getEmployeeById(String employeeId){
//        return employeeList.stream()
//                .filter(emp -> emp.getEmployeeId().equals(employeeId))
//                .findFirst() // Get the first matched EmployeeData
//                .orElse(null);
        System.out.println("dddbbbbb");
        return hashOperations.get(KEY, employeeId);
    }


    @Override
    public ResponseEntity<String> postData(EmployeeData emp) {
//        employeeList.add(emp);
        hashOperations.put(KEY, emp.getEmployeeId(), emp);
        return ResponseEntity.ok("employee added successfully");
    }
    @Override
    public ResponseEntity<String> dropEmployee(String employeeId){
//        employeeList = (List<EmployeeData>) employeeList.stream().filter(i-> !employeeId.equals(i.getEmployeeId())).collect(Collectors.toList());
        hashOperations.delete(KEY, employeeId);
        return ResponseEntity.ok("Deleted Employee Sucsessfully");
    }
    @Override
    public ResponseEntity<String> updateEmployee(EmployeeData emp){
//        int index=employeeList.indexOf(employeeList.stream()
//                .filter(employee -> employee.getEmployeeId().equals(emp.getEmployeeId()))
//                .findFirst()
//                .orElse(null));
//        employeeList.set(index,emp);
        hashOperations.put(KEY, emp.getEmployeeId(),emp);
        return  ResponseEntity.ok("updated employee successfully");
    }
}
