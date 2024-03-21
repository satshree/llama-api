package com.llama.api.billings.services;

import com.llama.api.billings.models.Billings;
import com.llama.api.billings.models.Orders;
import com.llama.api.billings.repository.BillingRepository;
import com.llama.api.billings.serializer.BillingSerialized;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BillingService {
    @Autowired
    BillingRepository billingRepository;

    @Autowired
    UserService userService;

    public List<Billings> getAllBillings() {
        return billingRepository.findAll();
    }

    public List<Billings> getAllBillings(String userID) throws ResourceNotFound {
        Users user = userService.getUser(userID);

        return billingRepository.findByUser(user);
    }

    public List<BillingSerialized> getAllBillingSerialized() {
        List<BillingSerialized> billingSerializedList = new ArrayList<>();

        for (Billings b : getAllBillings()) {
            billingSerializedList.add(BillingSerialized.serialize(b));
        }

        return billingSerializedList;
    }

    public List<BillingSerialized> getAllBillingSerialized(String userID) throws ResourceNotFound {
        List<BillingSerialized> billingSerializedList = new ArrayList<>();

        for (Billings b : getAllBillings(userID)) {
            billingSerializedList.add(BillingSerialized.serialize(b));
        }

        return billingSerializedList;
    }

    public Billings getBill(String id) throws ResourceNotFound {
        return billingRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Bill does not exist")
                );
    }

    public BillingSerialized getBillSerialized(String id) throws ResourceNotFound {
        return BillingSerialized.serialize(getBill(id));
    }

    public Billings setDiscount(String id, Double discount) throws ResourceNotFound {
        Billings billings = getBill(id);

        billings.setDiscount(discount);

        return billingRepository.save(billings);
    }

    public Billings updateTotal(String id) throws ResourceNotFound {
        Billings billings = getBill(id);

        for (Orders o : billings.getOrders()) {
            billings.setSubtotal(
                    billings.getSubtotal() + o.getTotal() // STOTAL = STOTAL + (TOTAL = UNIT PRICE * QUANTITY)
            );
        }

        billings.setTax(
                (billings.getSubtotal() / 100) * 6.25 // TEXAS SALES TAX
        );

        billings.setGrandTotal(
                billings.getSubtotal() + billings.getTax() - billings.getDiscount()
        );

        return billingRepository.save(billings);
    }

    public Billings createBill(String userID) throws ResourceNotFound {
        Users user = userService.getUser(userID);

        Billings billings = new Billings();

        billings.setUser(user);

        billings.setDiscount(0.0d);
        billings.setTax(0.0d);
        billings.setGrandTotal(0.0d);

        return billingRepository.save(billings);
    }

    public void deleteBill(String id) {
        billingRepository.deleteById(UUID.fromString(id));
    }
}
