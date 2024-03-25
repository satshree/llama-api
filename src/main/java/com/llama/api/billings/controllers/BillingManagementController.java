package com.llama.api.billings.controllers;

import com.llama.api.billings.serializer.BillingSerialized;
import com.llama.api.billings.services.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600) // change later to only accept from frontend applications
@RestController
@RequestMapping("/api/management/billing")
public class BillingManagementController {
    @Autowired
    BillingService billingService;

    @GetMapping("/")
    public ResponseEntity<List<BillingSerialized>> getBillings() {
        return ResponseEntity.ok(billingService.getAllBillingSerialized());
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Map<String, String>> deleteBilling(@PathVariable("id") String id) {
        billingService.deleteBill(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Bill deleted");

        return ResponseEntity.ok(response);
    }
}
