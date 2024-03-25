package com.llama.api.billings.serializer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.llama.api.billings.models.BillingInfo;
import com.llama.api.billings.services.BillingInfoService;
import com.llama.api.billings.services.BillingService;
import com.llama.api.exceptions.ResourceNotFound;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillingInfoSerialized {
    String id;
    @JsonProperty("bill_id")
    String billID;
    Customer customer;
    Address address;

    public static BillingInfoSerialized serialize(BillingInfo info) {
        BillingInfoSerialized billingInfoSerialized = new BillingInfoSerialized();
        BillingInfoService billingInfoService = new BillingInfoService();

        try {
            billingInfoSerialized.setBillID(
                    billingInfoService
                            .getBillID(info.getId().toString())
            );
        } catch (ResourceNotFound e) {
            return null;
        }


        billingInfoSerialized.setId(info.getId().toString());
        billingInfoSerialized.setCustomer(new Customer(
                info.getFirstName(),
                info.getLastName(),
                info.getEmail(),
                info.getPhone()
        ));
        billingInfoSerialized.setAddress(new Address(
                info.getAddress(),
                info.getCity(),
                info.getState(),
                info.getZip().toString(),
                "United States"
        ));

        return billingInfoSerialized;
    }
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Customer {
    @JsonProperty("first_name")
    String firstName;
    @JsonProperty("last_name")
    String lastName;
    String email;
    String phone;
}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Address {
    String street;
    String city;
    String state;
    String zip;
    String country = "United States";
}