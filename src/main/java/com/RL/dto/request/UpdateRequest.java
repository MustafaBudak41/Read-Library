package com.RL.dto.request;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.validation.constraints.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UpdateRequest {

        @Size(max = 30)
        @NotNull(message = "Please provide your first name")
        private String firstName;

        @Size(max = 30)
        @NotNull(message = "Please provide your last name")
        private String lastName;

        @Size(max = 100)
        @NotNull(message = "Please provide your address")
        private String address;

        @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$", message = "Please provide valid phone number")
        @Size(min = 14, max = 14)
        @NotNull(message = "Please provide your phone number")
        private String phone;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        @NotNull(message="Please provide birthDate")
        private Date birthDate;

        @Email(message = "Please provide valid email")
        @NotNull(message="Please provide email")
        @Size(min=10, max=80,message="Email '${validatedValue}' must be between {min} and {max} chars long")
        private String email;

        @NotNull(message="Please provide password")
        private String password;


}
