package com.llama.api.analytics;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsResponse {
    Long totalSales;
    Double totalCashFlow;
    Long totalProducts;
    UserData totalUsers;

    public void setTotalUsers(Long total, Long adminUser) {
        totalUsers = new UserData(
                total, adminUser
        );
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class UserData {
    Long total;
    Long adminUser;
}