package money.jupiter.employeemanagement.services.cache;

import money.jupiter.employeemanagement.models.Employee;
import money.jupiter.employeemanagement.models.Student;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class CacheService {
    private final HashOperations<String, String, Student> hashOperationsStudent;
    private final HashOperations<String, String, Employee> hashOperationsEmployee;
    private final RedisTemplate<String,Student> redisTemplateStudent;

    private final RedisTemplate<String, Employee> redisTemplateEmployee;
    public CacheService(RedisTemplate<String,Student> redisTemplate,
                        HashOperations<String, String, Student> hashOperationsStudent,
                        HashOperations<String, String, Employee> hashOperationsEmployee,
                        RedisTemplate<String, Student> redisTemplateStudent,
                        RedisTemplate<String, Employee> redisTemplateEmployee) {
        this.hashOperationsStudent = hashOperationsStudent;
        this.hashOperationsEmployee = hashOperationsEmployee;
        this.redisTemplateStudent = redisTemplateStudent;
        this.redisTemplateEmployee = redisTemplateEmployee;

    }
    public void putStudent(Student student){
        hashOperationsStudent.put("student",student.getStudentId(),student);
    }

    public Student getStudent(String id){
        return hashOperationsStudent.get("student",id);
    }

    public void putEmployee(Employee employee){
        hashOperationsEmployee.put("employee",employee.getEmployeeId(),employee);
    }

    public Employee getEmployee(String id){
        return hashOperationsEmployee.get("employee",id);
    }

}
