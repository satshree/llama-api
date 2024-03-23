package com.llama.api.billings.models;

import com.llama.api.users.models.Users;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Billings")
@Data
@NoArgsConstructor
public class Billings {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "billing_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    Double subtotal;

    @Column
    Double discount;

    @Column
    Double tax;

    @Column(name = "grand_total")
    Double grandTotal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    Users user;

    @OneToMany(mappedBy = "bill")
    List<Orders> orders;

    @OneToMany(mappedBy = "bill")
    List<Paid> paidList;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "binfo_id")
    BillingInfo billingInfo;
}
