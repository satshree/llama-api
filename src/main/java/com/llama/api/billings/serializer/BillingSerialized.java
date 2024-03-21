package com.llama.api.billings.serializer;

import com.llama.api.billings.models.Billings;
import com.llama.api.users.serializer.SimpleUserSerialized;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingSerialized {
    String id;
    Double subtotal;
    Double discount;
    Double tax;
    Double grand_total;

    SimpleUserSerialized user;
    List<OrderSerialized> orders;

    public static BillingSerialized serialize(Billings billing) {
        return new BillingSerialized(
                billing.getId().toString(),
                billing.getSubtotal(),
                billing.getDiscount(),
                billing.getTax(),
                billing.getGrandTotal(),
                SimpleUserSerialized.serialize(billing.getUser()),
                OrderSerialized.serialize(billing.getOrders())
        );
    }
}
