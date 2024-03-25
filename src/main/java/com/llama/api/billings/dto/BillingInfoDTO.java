package com.llama.api.billings.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingInfoDTO {
    @JsonProperty("bill_id")
    String billID;
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
    String email;
    String phone;
    String address;
    String city;
    String state;
    Integer zip;
}
