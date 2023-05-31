package com.example.learntesting;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    @Column(nullable = false)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    @NotBlank
    @Column(nullable = false)
    private String username;
    @NotBlank
    @Column(nullable = false)
    private String password;

    private Date createAt = new Date(new java.util.Date().getTime());

    public Student(String fullName, Gender gender, String username, String password, Date createAt) {
        this.fullName = fullName;
        this.gender = gender;
        this.username = username;
        this.password = password;
        this.createAt = createAt;
    }
}
