package com.llama.api.billings.controllers;

import com.llama.api.billings.dto.BillingDTO;
import com.llama.api.billings.dto.PaidDTO;
import com.llama.api.billings.models.Billings;
import com.llama.api.billings.serializer.BillingSerialized;
import com.llama.api.billings.serializer.PaidSerialized;
import com.llama.api.billings.services.BillingService;
import com.llama.api.billings.services.OrderService;
import com.llama.api.billings.services.PaidService;
import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.services.CartItemService;
import com.llama.api.cart.services.CartService;
import com.llama.api.exceptions.ResourceNotFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600) // change later to only accept from frontend applications
@RestController
@RequestMapping("/api/website/billing")
public class BillingController {
    @Autowired
    BillingService billingService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartService cartService;

    @Autowired
    CartItemService cartItemService;

    @Autowired
    PaidService paidService;

    @GetMapping("/get-my-bills/{userID}/")
    public ResponseEntity<List<BillingSerialized>> getBills(@PathVariable("userID") String userID) throws ResourceNotFound {
        return ResponseEntity.ok(billingService.getAllBillingSerialized(userID));
    }

    @GetMapping("/get/{id}/")
    public ResponseEntity<BillingSerialized> getBillByID(@PathVariable("id") String id) throws ResourceNotFound {
        return ResponseEntity.ok(billingService.getBillSerialized(id));
    }

    @PostMapping("/create/")
    public ResponseEntity<BillingSerialized> createBill(@Valid @RequestBody BillingDTO billingDTO) throws ResourceNotFound {
        Billings billings = billingService.createBill(billingDTO.getInfo());
        Cart cart = cartService.getCartByID(billingDTO.getCartID());

        for (CartItems i : cart.getCartItems()) {
            orderService.createOrder(
                    billings.getId().toString(),
                    i.getProduct().getId().toString(),
                    i.getQuantity()
            );

            cartItemService.deleteItem(i.getId().toString()); // REMOVE ITEM FROM CART AFTER ADDING TO BILL
        }

        billings = billingService.getBill(billings.getId().toString()); // UPDATE TO FETCH ORDERS AND TOTAL VALUES

        return ResponseEntity.ok(BillingSerialized.serialize(billings));
    }

    @PostMapping("/pay/{id}/")
    public ResponseEntity<PaidSerialized> addPayment(@PathVariable("id") String id, @Valid @RequestBody PaidDTO paidDTO) throws ResourceNotFound {
        paidDTO.setBillID(id);

        return ResponseEntity.ok(
                PaidSerialized.serialize(
                        paidService.addPaid(paidDTO)
                )
        );
    }

}
