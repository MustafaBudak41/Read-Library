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
    //bu dto disari donmek icin
    /*
    @NotNull
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date created;
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm:ss")
    private Date updated;
     */
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String phone;

    private Date birthDate;

    private String email;

    private Boolean builtIn ;

    private Set<String> roles;//db de ki enum role type lari string olarak donduk

    public void setRoles(Set<Role> roles) {
        Set<String> rolesStr = new HashSet<>();

        roles.forEach(m-> {//role mapping islmelri
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
