package money.jupiter.employeemanagement.models;

import lombok.*;
import org.springframework.core.serializer.Serializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeData implements Serializable{

    private String firstName;
    private String lastName;
    private String employeeId;

}
