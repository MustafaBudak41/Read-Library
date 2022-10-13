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
@Table(name="tbl_author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message="Name can not be null")
    @NotBlank(message="Name can not be white space")
    @Size(min=4,max=70, message="Name '${validatedValue}' must be between {min} and {max} chars long")
    @Column(length = 70, nullable = false)
    private String name;

    private Boolean builtIn=false;

    @OneToMany(mappedBy="authorId")
    private List<Book> books;

}