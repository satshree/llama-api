package com.llama.api.billings.serializer;

import com.llama.api.billings.models.Billings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingSerialized {
    String id;
    BillingInfoSerialized info;
    Double subtotal;
    Double discount;
    Double tax;
    Double grand_total;

    List<PaidSerialized> paid;
    List<OrderSerialized> orders;

    public static BillingSerialized serialize(Billings billing) {
        return new BillingSerialized(
                billing.getId().toString(),
                BillingInfoSerialized.serialize(billing.getBillingInfo()),
                billing.getSubtotal(),
                billing.getDiscount(),
                billing.getTax(),
                billing.getGrandTotal(),
                PaidSerialized.serialize(billing.getPaidList()),
                OrderSerialized.serialize(billing.getOrders())
        );
    }
}
