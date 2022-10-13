package com.RL.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Name can not be null")
    @NotBlank(message="Name can not be white space")
    @Size(min=2,max=80, message="Name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 80, nullable = false)
    private String name;

    private Boolean builtIn=false;

    @NotNull(message="Sequence can not be null")
    private int sequence;

    @OneToMany(mappedBy="categoryId")
    private List<Book> book;


}