package com.llama.api.billings.services;

import com.llama.api.billings.models.Billings;
import com.llama.api.billings.models.Orders;
import com.llama.api.billings.repository.OrderRepository;
import com.llama.api.billings.serializer.OrderSerialized;
import com.llama.api.exceptions.ResourceNotFound;
import com.llama.api.products.models.Products;
import com.llama.api.products.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

//import static java.lang.String.format;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    BillingService billingService;

    @Autowired
    ProductService productService;

    public List<Orders> getOrders(String billID) throws ResourceNotFound {
        Billings bill = billingService.getBill(billID);

        return orderRepository.findByBill(bill);
    }

    public List<OrderSerialized> getOrdersSerialized(String billID) throws ResourceNotFound {
        return OrderSerialized.serialize(getOrders(billID));
    }

    public Orders getOrder(String id) throws ResourceNotFound {
        return orderRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Order does not exist")
                );
    }

    public OrderSerialized getOrderSerialized(String id) throws ResourceNotFound {
        return OrderSerialized.serialize(getOrder(id));
    }

    public Orders createOrder(String productID, String billID, Integer quantity) throws ResourceNotFound {
        Products product = productService.getProduct(productID);
        Billings bill = billingService.getBill(billID);

        Orders order = new Orders();

        order.setBill(bill);
        order.setProduct(product);
        order.setUnitPrice(product.getPrice());

        order.setTitle(product.getName());

        order = orderRepository.save(order);

        // SET QUANTITY AND UPDATE TOTAL
        order = updateQuantity(order.getId().toString(), quantity);

        return order;
    }

    public Orders updateTotal(String orderID) throws ResourceNotFound {
        Orders order = getOrder(orderID);

        order.setTotal(
                order.getUnitPrice() * order.getQuantity()
        );

        order = orderRepository.save(order);

        // UPDATE BILL
        billingService.updateTotal(
                order.getBill().getId().toString()
        );

        return order;
    }

    public Orders updateQuantity(String orderID, Integer quantity) throws ResourceNotFound {
        Orders order = getOrder(orderID);
        order.setQuantity(quantity); // implement check for positive integer later!!

        orderRepository.save(order);

        return updateTotal(orderID);
    }
}
