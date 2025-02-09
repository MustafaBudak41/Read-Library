package com.RL.domain;


import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.io.File;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 80, nullable = false)
    private String name;

    @Column(length = 17, nullable = false)
    private String isbn;

    @Column
    private int pageCount;

    @Column
    private int publishDate;

    @Column
    private File image;

    @Column(nullable = false)
    private boolean loanable=true;

    @Column(length = 6, nullable = false)
    private String shelfCode;

    @Column(nullable = false)
    private boolean active=true;

    @Column(nullable = false)
    private boolean featured=false;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern =
            "MM/dd/yyyy HH:mm:ss", timezone = "Turkey")
    @Column(nullable = false)
    private LocalDateTime createDate=LocalDateTime.now();

    @Column(nullable = false)
    private boolean builtIn=false;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    @JsonIgnoreProperties("books")
    private Author authorId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", nullable = false)
    @JsonIgnoreProperties("books")
    private Publisher publisherId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnoreProperties("books")
    private Category categoryId;

    @OneToMany(mappedBy = "bookId")
    @JsonIgnoreProperties("bookId")
    private List<Loan> loansList;


}