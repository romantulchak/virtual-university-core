package com.romantulchak.virtualuniversity.model;

import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class Address {

    @NotBlank(message = "Street is required")
    @JsonView(Views.StudentView.class)
    private String street;

    @NotBlank(message = "Postal Code is required")
    @JsonView(Views.StudentView.class)
    private String postalCode;

    @NotBlank(message = "City is required")
    @JsonView(Views.StudentView.class)
    private String city;


    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
