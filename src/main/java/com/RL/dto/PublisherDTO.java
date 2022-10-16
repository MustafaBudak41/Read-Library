package com.RL.dto;

import com.RL.domain.Role;
import com.RL.domain.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PublisherDTO {

    @NotNull(message = "Please enter name Publisher")
    private String name;

    private Set<String> roles;

    public void setRoles(Set<Role> roles){
        Set<String> roleStr = new HashSet<>();

        roles.forEach(r-> {
            if(r.getName().equals(RoleType.ROLE_ADMIN))
                roleStr.add("Administrator");
            else if(r.getName().equals(RoleType.ROLE_ANONYMOUS))
                roleStr.add("Member");
            else if(r.getName().equals(RoleType.ROLE_EMPLOYEE))
                roleStr.add("Employee");
            else
                roleStr.add("Anonymous");

        });
        this.roles=roleStr;
    }
}
