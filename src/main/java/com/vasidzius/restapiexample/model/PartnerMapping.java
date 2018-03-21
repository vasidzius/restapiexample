package com.vasidzius.restapiexample.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@Table(
        uniqueConstraints =
        @UniqueConstraint(columnNames = {"partnerId", "accountId"})
)
@Entity
public class PartnerMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //идентификатор партнера (например, идентификатор конкретного приложения в социальной сети Facebook)
    private long partnerId;
    //идентификатор аккаунта абонента в этой партнерской системе (например, id профиля абонента в соцсети)
    private long accountId;
    @ManyToOne()
    @JoinColumn(name="customer_Id")
    @JsonBackReference
    private Customer customer;
    @NotBlank(message = "Please enter name")
    @Size(max = 20, message = "Name must be at most 20 characters")
    private String name;
    @NotBlank(message = "Please enter surname")
    @Size(max = 20, message = "Surname must be at most 20 characters")
    private String surname;
    @Size(max = 20, message = "Patronymic must be at most 20 characters")
    private String patronymic;
    private byte[] avatar;
}
