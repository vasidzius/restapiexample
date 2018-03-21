package com.vasidzius.restapiexample.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Customer {
    @Id
    private Long id;

    @NotBlank(message = "Please enter name")
    @Size(max = 20, message = "Name must be at most 20 characters")
    private String name;

    @NotBlank(message = "Please enter surname")
    @Size(max = 20, message = "Surname must be at most 20 characters")
    private String surname;
    @Size(max = 20, message = "Patronymic must be at most 20 characters")
    private String patronymic;
    @Digits(integer = 6, fraction = 2)
    private Double balance;
    private Boolean isBlocked;
    @NotBlank(message = "Please enter login")
    private String login;
    @Size(min = 6, message = "Password must be at least 6 characters")
    private Character[] password;
    @OneToMany(mappedBy="customer")
    @JsonManagedReference
    private List<PartnerMapping> partnerMappings;
}
