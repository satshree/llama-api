package com.llama.api.analytics;

import com.llama.api.billings.models.Billings;
import com.llama.api.billings.services.BillingService;
import com.llama.api.products.services.ProductService;
import com.llama.api.users.models.Users;
import com.llama.api.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@RestController
@RequestMapping("/api/management/analytics")
public class AnalyticsController {
    @Autowired
    BillingService billingService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @GetMapping("/")
    public ResponseEntity<AnalyticsResponse> getAnalytics() {
        AnalyticsResponse response = new AnalyticsResponse();

        List<Billings> billings = billingService.getAllBillings();

        Long totalSales = (long) billings.size();

        AtomicReference<Double> totalCashflow = new AtomicReference<>(0.0d);
        billings
                .forEach(
                        (bill) -> bill
                                .getPaidList()
                                .forEach(
                                        (paid) -> totalCashflow.updateAndGet(v -> v + paid.getAmount())
                                ));

        Long totalProducts = (long) productService.getAllProducts().size();

        Long totalUsers = (long) userService.getAllUsers().size();

        Long adminUsers = (long) userService.getAllSuperUsers().size();

        response.setTotalSales(totalSales);
        response.setTotalProducts(totalProducts);
        response.setTotalCashFlow(totalCashflow.get());
        response.setTotalUsers(totalUsers, adminUsers);

        return ResponseEntity.ok(response);
    }
}
