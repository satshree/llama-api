package com.llama.api.billings.serializer;

import com.llama.api.Utils;
import com.llama.api.billings.models.Billings;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    String date;

    List<PaidSerialized> paid;
    List<OrderSerialized> orders;

    public static BillingSerialized serialize(Billings billing) {
        List<PaidSerialized> paid = new ArrayList<>();
        List<OrderSerialized> orders = new ArrayList<>();

        if (billing.getOrders() != null) {
            orders = OrderSerialized.serialize(billing.getOrders());
        }

        if (billing.getPaidList() != null) {
            paid = PaidSerialized.serialize(billing.getPaidList());
        }

        return new BillingSerialized(
                billing.getId().toString(),
                BillingInfoSerialized.serialize(billing.getBillingInfo()),
                billing.getSubtotal(),
                billing.getDiscount(),
                billing.getTax(),
                billing.getGrandTotal(),
                Utils.parseDate(billing.getDate()),
                paid,
                orders
        );
    }

    public static List<BillingSerialized> serialize(List<Billings> billings) {
        List<BillingSerialized> billingSerializedList = new ArrayList<>();

        for (Billings b : billings) {
            billingSerializedList.add(serialize(b));
        }
        
        return billingSerializedList;
    }
}