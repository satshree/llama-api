package com.llama.api.billings.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Paid")
@Data
@NoArgsConstructor
public class Paid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "paid_id", columnDefinition = "uuid", nullable = false, updatable = false)
    UUID id;

    @Column
    String card;

    @Column
    Double amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "billing_id")
    Billings bill;
}
