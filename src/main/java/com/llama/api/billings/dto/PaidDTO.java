package com.llama.api.billings.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaidDTO {
    String card;
    Double amount;
    @JsonProperty("bill_id")
    String billID;
}
