package com.llama.api.billings.controllers;

import com.llama.api.billings.dto.BillingDTO;
import com.llama.api.billings.dto.PaidDTO;
import com.llama.api.billings.models.Billings;
//import com.llama.api.billings.models.Orders;
import com.llama.api.billings.serializer.BillingSerialized;
//import com.llama.api.billings.serializer.OrderSerialized;
import com.llama.api.billings.serializer.PaidSerialized;
import com.llama.api.billings.services.BillingService;
import com.llama.api.billings.services.OrderService;
import com.llama.api.billings.services.PaidService;
import com.llama.api.cart.models.Cart;
import com.llama.api.cart.models.CartItems;
import com.llama.api.cart.repository.CartItemRepository;
import com.llama.api.cart.repository.CartRepository;
import com.llama.api.cart.services.CartService;
import com.llama.api.exceptions.ResourceNotFound;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    PaidService paidService;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartItemRepository cartItemRepository;

    @GetMapping("/get-my-bills/{userID}/")
    public ResponseEntity<List<BillingSerialized>> getBills(@PathVariable("userID") String userID) throws ResourceNotFound {
        return ResponseEntity.ok(billingService.getAllBillingSerialized(userID));
    }

    @GetMapping("/get-by-phone/")
    public ResponseEntity<List<BillingSerialized>> getBillsByPhone(@RequestParam(name = "phone", required = true) String phone) throws ResourceNotFound {
        return ResponseEntity.ok(
                BillingSerialized.serialize(
                        billingService
                                .getAllBillings()
                                .stream()
                                .filter(billings ->
                                        billings
                                                .getBillingInfo()
                                                .getPhone()
                                                .equals(phone)
                                )
                                .collect(Collectors.toList())
                )
        );

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
                    i.getProduct().getId().toString(),
                    billings,
                    i.getQuantity()
            );
            cartItemRepository.forceDeleteItem(i.getId());
        }

        cart.setTotal(0.0d);
        cartRepository.save(cart);

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
