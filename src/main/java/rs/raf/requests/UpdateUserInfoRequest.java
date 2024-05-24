package rs.raf.requests;

import lombok.*;
import rs.raf.entities.UserType;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UpdateUserInfoRequest {

    @NotEmpty(message = "Old email field is required")
    @NotNull(message = "Old email field is required")
    @Email(message = "Old email field is not valid")
    private String oldEmail;

    @NotEmpty(message = "New email field is required")
    @NotNull(message = "New email field is required")
    @Email(message = "New email field is not valid")
    private String newEmail;

    @NotEmpty(message = "Name field is required")
    @NotNull(message = "Name field is required")
    private String name;

    @NotEmpty(message = "Surname field is required")
    @NotNull(message = "Surname field is required")
    private String surname;

    @NotNull(message = "UserType field is required")
    private UserType userType;
}
