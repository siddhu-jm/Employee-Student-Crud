package money.jupiter.employeemanagement.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {


    private String firstName;
    private String lastName;
    private int std;
    private String studentId;

}
