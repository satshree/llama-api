package com.llama.api.billings.controllers;

import com.llama.api.Utils;
import com.llama.api.billings.models.Billings;
import com.llama.api.billings.serializer.BillingSerialized;
import com.llama.api.billings.services.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/management/billing")
public class BillingManagementController {
    @Autowired
    BillingService billingService;

    @GetMapping("/")
    public ResponseEntity<List<BillingSerialized>> getBillings(@RequestParam(name = "date", required = false) String date) throws ParseException {
        List<Billings> billingList = billingService.getAllBillings();
        if (date != null) {
            LocalDate filterDate = Utils.convertToLocalDate(date);
            billingList = billingList
                    .stream()
                    .filter(bill -> Utils.convertToLocalDate(bill.getDate()).equals(filterDate))
                    .collect(Collectors.toList());
        }

        return ResponseEntity.ok(BillingSerialized.serialize(billingList));
    }

    @DeleteMapping("/{id}/")
    public ResponseEntity<Map<String, String>> deleteBilling(@PathVariable("id") String id) {
        billingService.deleteBill(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Bill deleted");

        return ResponseEntity.ok(response);
    }
}
