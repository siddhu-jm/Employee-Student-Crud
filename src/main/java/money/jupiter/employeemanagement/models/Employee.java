package money.jupiter.employeemanagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee {

    private String firstName;
    private String lastName;
    @Id
    private String employeeId;

}
