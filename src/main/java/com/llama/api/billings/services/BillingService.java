package com.llama.api.billings.services;

import com.llama.api.billings.dto.BillingInfoDTO;
import com.llama.api.billings.models.BillingInfo;
import com.llama.api.billings.models.Billings;
import com.llama.api.billings.models.Orders;
import com.llama.api.billings.repository.BillingRepository;
import com.llama.api.billings.repository.OrderRepository;
import com.llama.api.billings.serializer.BillingSerialized;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class BillingService {
    @Autowired
    BillingRepository billingRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserService userService;

    @Autowired
    BillingInfoService billingInfoService;

    public List<Billings> getAllBillings() {
        return billingRepository.findAll();
    }

    public List<Billings> getAllBillings(String userID) throws ResourceNotFound {
        Users user = userService.getUser(userID);
        List<BillingInfo> billingInfoList = billingInfoService
                .getBillingInfoByEmail(
                        user.getEmail()
                );

        List<Billings> billings = new ArrayList<>();

        for (BillingInfo b : billingInfoList) {
            billings.add(
                    billingRepository.findByBillingInfo(b)
            );
        }

        return billings;
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
            if (b != null) {
                billingSerializedList.add(BillingSerialized.serialize(b));
            }
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

    /**
     * Use this method with Billings parameter to update bill total when adding order
     *
     * @param id
     * @return Billings
     */
    public Billings updateTotal(String id) throws ResourceNotFound {
        Billings billings = getBill(id);

        List<Orders> orders = orderRepository.findByBill(billings);
        if (billings.getOrders() != null) {
            orders = billings.getOrders();
        }

        for (Orders o : orders) {
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

    /**
     * Use this method to update bill total when adding order
     *
     * @param billings
     * @return Billings
     */
    public Billings updateTotal(Billings billings) {
        List<Orders> orders = orderRepository.findByBill(billings);
        if (billings.getOrders() != null) {
            orders = billings.getOrders();
        }

        for (Orders o : orders) {
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

    public Billings createBill(BillingInfoDTO billingInfoDTO) {
        Billings billings = new Billings();
        BillingInfo billingInfo = new BillingInfo();

        BeanUtils.copyProperties(billingInfoDTO, billingInfo);
        billings.setBillingInfo(
                billingInfo
        );

        billings.setDate(new Date());
        billings.setSubtotal(0.0d);
        billings.setDiscount(0.0d);
        billings.setTax(0.0d);
        billings.setGrandTotal(0.0d);

        return billingRepository.save(billings);
    }

    public void deleteBill(String id) {
        billingRepository.deleteById(UUID.fromString(id));
    }
}
