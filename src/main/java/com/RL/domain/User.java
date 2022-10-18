package com.RL.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 30,nullable = false)
    private String firstName;

    @Column(length = 30,nullable = false)
    private String lastName;

    @DecimalMax(value="2", message="Score '${validatedValue}' must be max {value} ")
    @DecimalMin(value="-2", message="Score '${validatedValue}' must be min {value}")
    @Column(nullable = false)
    private Integer score =0;

    @Column(length = 100,nullable = false)
    private String address;

    @Column(length = 14,nullable = false)
    private String phone;

    @Column(length = 20,nullable = false)
    private Date birthDate;

    @Column(length=80,nullable = false,unique = true)
    private String email;

    @Column(length = 120 ,nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDateTime createDate=LocalDateTime.now();

    @Column(length =120,nullable=false)
    private String resetPasswordCode;

    @Column(nullable = false)
    private Boolean builtIn = false;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="tbl_user_role",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy="userId")
    private List<Loan> loan;//new ArrayList<>();


}