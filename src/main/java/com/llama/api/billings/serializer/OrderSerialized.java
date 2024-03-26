package com.llama.api.billings.serializer;

import com.llama.api.billings.models.Orders;
import com.llama.api.products.serializer.SimpleProductSerialized;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderSerialized {
    String id;
    String title;
    String bill;
    Double unit_price;
    Integer quantity;
    Double total;
    SimpleProductSerialized product;

    public static OrderSerialized serialize(Orders order) {
        return new OrderSerialized(
                order.getId().toString(),
                order.getTitle(),
                order.getBill().getId().toString(),
                order.getUnitPrice(),
                order.getQuantity(),
                order.getTotal(),
                SimpleProductSerialized.serialize(order.getProduct())
        );
    }

    public static List<OrderSerialized> serialize(List<Orders> orders) {
        List<OrderSerialized> orderSerializedList = new ArrayList<>();

        for (Orders o : orders) {
            orderSerializedList.add(OrderSerialized.serialize(o));
        }

        return orderSerializedList;
    }
}
