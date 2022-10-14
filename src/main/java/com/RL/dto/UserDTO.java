package com.RL.dto;

import com.RL.domain.Role;
import com.RL.domain.enums.RoleType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {//datalogic ile ilgili hicbirsey burda olmamali
    /*
    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date created;
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date updated;
     */


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
    @Size(min=12, max=12,message="Phone number '${validatedValue}' must be {max} chars long")
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$",message = "Please provide valid phone number")
    private String phone;

    @NotNull(message="Please provide birthDate")
    private Date birthDate;

    @Email(message = "Please provide valid email")
    @NotNull(message="Please provide email")
    @Size(min=10, max=80,message="Email '${validatedValue}' must be between {min} and {max} chars long")
    private String email;

    @NotNull(message="Please provide password")
    private String password;

    private Set<String> roles;

    public void setRoles(Set<Role> roles) {
        Set<String> rolesStr = new HashSet<>();

        roles.forEach(m-> {
            if (m.getName().equals(RoleType.ROLE_ADMIN))
                rolesStr.add("Administrator");
            else if(m.getName().equals(RoleType.ROLE_MEMBER))
                rolesStr.add("Member");
            else if(m.getName().equals(RoleType.ROLE_EMPLOYEE))
                rolesStr.add("Employee");
            else
                rolesStr.add("Anonymous");
        });

        this.roles=rolesStr;
    }


}
