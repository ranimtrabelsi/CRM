package org.taskspfe.pfe.dto.auth;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data

public class RegisterDTO {

    @Size(min = 2 , max = 10, message ="Invalid name length. Please enter a name with a minimum of 5 characters and a maximum of 20 characters.")
    private String firstName;
    @Size(min = 2 , max = 10, message ="Invalid name length. Please enter a name with a minimum of 5 characters and a maximum of 20 characters.")
    private String lastName;
    @Pattern(regexp = "[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*@[a-zA-Z0-9]+(?:\\.[a-zA-Z0-9]+)*$", message = "Invalid email address. Please enter a valid email.")
    private String email;

    private String phoneNumber;

    @Size(min = 8 , max = 20, message ="Invalid password length. Please enter a password with a minimum of 8 characters and a maximum of 20 characters.")
    private String password;

    public RegisterDTO(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


}
