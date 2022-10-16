package com.RL.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {


        @NotNull(message="Please provide first name")
        @Size(min=2, max=30,message="Your first name '${validatedValue}' must be between {min} and {max} chars long")
        private String firstName;

        @NotNull(message="Please provide last name")
        @Size(min=2, max=30,message="Your last name '${validatedValue}' must be between {min} and {max} chars long")
        private String lastName;

        @NotNull(message="Please provide address")
        @Size(min=10, max=100,message="Address '${validatedValue}' must be between {min} and {max} chars long")
        private String address;

        @NotNull(message="Please provide phone number")
        @Size(min = 14, max = 14,message="Phone number '${validatedValue}' must be {max} chars long")
        @Pattern(regexp = "^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$",
                message = "Please provide valid phone number")
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