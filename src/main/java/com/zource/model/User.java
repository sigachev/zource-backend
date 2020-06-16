package com.zource.model;

import antlr.StringUtils;
import lombok.Data;
import org.apache.commons.lang.WordUtils;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Entity
@Table(name="user")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotNull(message = "Name may not be null")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Name may not be null")
    private String lastName;


    @Column(name="username", unique = true)
    @NotNull(message = "Name may not be null")
    @NotEmpty(message = "Name may not be empty")
    private String username;

    @Column(name="email", unique = true)
    @NotEmpty(message = "Name may not be empty")
    @Email
    private String email;

    @PrePersist
    @PreUpdate
    private void prepareData(){
        this.email = email == null ? null : email.toLowerCase();
        this.username = username == null ? null : username.toLowerCase();
        this.firstName = firstName == null ? null : WordUtils.capitalize(firstName.toLowerCase());
        this.lastName = lastName == null ? null : WordUtils.capitalize(lastName.toLowerCase());
    }

    @Column(name="tel")
    private String tel;


    @Column(name="password")
    @NotEmpty(message = "Name may not be empty")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="role")
    private Role role;

    @Transient
    private String token;
}
