package com.romantulchak.virtualuniversity.model;

import com.romantulchak.virtualuniversity.model.enumes.Gender;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Objects;


@MappedSuperclass
public abstract class UserAbstract {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @NotBlank(message = "First Name is required")
    private String firstName;

    @NotBlank(message = "Last Name is required")
    private String lastName;

    @Column(unique = true)
    @NotBlank(message = "Login is required")
    private String login;

    private String password;

    @Email
    private String privateEmail;

    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String numberIdentifier;


    public UserAbstract(){}

    public UserAbstract(String firstName, String lastName, Gender gender, String privateEmail, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.privateEmail = privateEmail;
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getPrivateEmail() {
        return privateEmail;
    }

    public void setPrivateEmail(String privateEmail) {
        this.privateEmail = privateEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumberIdentifier() {
        return numberIdentifier;
    }

    public void setNumberIdentifier(String numberIdentifier) {
        this.numberIdentifier = numberIdentifier;
    }

    public String getFullName(){
        return firstName + " " + lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAbstract that = (UserAbstract) o;
        return id == that.id && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
