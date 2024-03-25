package com.llama.api.billings.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingDTO {
    @JsonProperty("cart_id")
    String cartID;
    BillingInfoDTO info;
}
