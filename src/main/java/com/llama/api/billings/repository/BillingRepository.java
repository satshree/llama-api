package com.llama.api.billings.repository;

import com.llama.api.billings.models.BillingInfo;
import com.llama.api.billings.models.Billings;
//import com.llama.api.billings.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;

//import java.util.List;
import java.util.UUID;

public interface BillingRepository extends JpaRepository<Billings, UUID> {
//    public List<Billings> findByUser(Users user);

//    @Query("SELECT o FROM Orders o WHERE o.bill = :billings")
//    List<Orders> getOrdersByBillings(Billings billings);

    public Billings findByBillingInfo(BillingInfo billingInfo);
}
