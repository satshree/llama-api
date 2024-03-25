package com.llama.api.billings.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "BillingInfo")
@Data
@NoArgsConstructor
public class BillingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "binfo_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String firstName;

    @Column
    String lastName;

    @Column
    String email;

    @Column
    String phone;

    @Column
    String address;

    @Column
    String city;

    @Column
    String state;

    @Column
    Integer zip;
}
