package money.jupiter.employeemanagement.services.cache;

import money.jupiter.employeemanagement.models.Employee;
import money.jupiter.employeemanagement.models.Student;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private final HashOperations<String, String, Student> hashOperationsStud;

    private final HashOperations<String, String, Employee> hashOperationsEmp;
    private final RedisTemplate<String,Student> redisTemplateStudent;
    private final RedisTemplate<String, Employee> redisTemplateEmployee;
    public CacheService(RedisTemplate<String,Employee> redisTemplateEmp,RedisTemplate<String,Student> redisTemplateStud, RedisTemplate<String, Student> redisTemplateStudent, RedisTemplate<String, Employee> redisTemplateEmployee) {
        this.redisTemplateStudent = redisTemplateStudent;
        this.redisTemplateEmployee = redisTemplateEmployee;
//        this.hashOperations = redisTemplate.opsForHash();
        this.hashOperationsEmp = redisTemplateEmp.opsForHash();
        this.hashOperationsStud = redisTemplateStud.opsForHash();
    }
    public void putStudent(Student student){
        hashOperationsStud.put("student",student.getStudentId(),student);
    }

    public Student getStudent(String id){
        return hashOperationsStud.get("student",id);
    }

    public void putEmployee(Employee employee){
        hashOperationsEmp.put("employee",employee.getEmployeeId(),employee);
    }

    public Employee getEmployee(String id){
        return hashOperationsEmp.get("employee",id);
    }

}
