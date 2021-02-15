package com.romantulchak.virtualuniversity.model;

import javax.persistence.Embeddable;

@Embeddable
public class Address {
    private String  street;
    private String postalCode;
    private String city;


}
