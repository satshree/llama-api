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

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    List<Orders> orders;

    @OneToMany(mappedBy = "bill", cascade = CascadeType.ALL)
    List<Paid> paidList;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "binfo_id")
    BillingInfo billingInfo;
}
