package com.llama.api.billings.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdersDTO {
    @JsonProperty("bill_id")
    String billID = null;
    @JsonProperty("product_id")
    String productID;
    Integer quantity = 1;
}
