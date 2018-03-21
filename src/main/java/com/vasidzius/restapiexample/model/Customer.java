package com.vasidzius.restapiexample.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
//    private String surname;
//    private String patronymic;
//    private Double balance;
//    private Boolean isBlocked;
//    private String login;
//    private Character[] password;

    public Customer() {
    }

}
