package money.jupiter.employeemanagement.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Student {


    private String firstName;
    private String lastName;
    private int std;
    @Id
    private String studentId;

}
