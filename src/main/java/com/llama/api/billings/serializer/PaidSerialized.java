package com.llama.api.billings.serializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.llama.api.billings.models.Paid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaidSerialized {
    String id;
    String card;
    Double amount;
    @JsonProperty("bill_id")
    String billID;

    public static PaidSerialized serialize(Paid paid) {
        return new PaidSerialized(
                paid.getId().toString(),
                paid.getCard(),
                paid.getAmount(),
                paid.getBill().getId().toString()
        );
    }

    public static List<PaidSerialized> serialize(List<Paid> paidList) {
        List<PaidSerialized> paidSerializedList = new ArrayList<>();

        for (Paid p : paidList) {
            paidSerializedList.add(serialize(p));
        }

        return paidSerializedList;
    }
}
