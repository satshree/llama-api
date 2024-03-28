package com.llama.api.billings.services;

import com.llama.api.billings.dto.PaidDTO;
import com.llama.api.billings.models.Billings;
import com.llama.api.billings.models.Paid;
import com.llama.api.billings.repository.PaidRepository;
import com.llama.api.billings.serializer.PaidSerialized;
import com.llama.api.exceptions.ResourceNotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaidService {
    @Autowired
    PaidRepository paidRepository;

    @Autowired
    BillingService billingService;

    public Paid getPaid(String id) throws ResourceNotFound {
        return paidRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("No paid data found")
                );
    }

    public PaidSerialized getPaidSerialized(String id) throws ResourceNotFound {
        return PaidSerialized.serialize(getPaid(id));
    }

    public List<PaidSerialized> getPaidSerialized(List<Paid> paidList) {
        return PaidSerialized.serialize(paidList);
    }

    public List<PaidSerialized> getPaidByBill(String billID) throws ResourceNotFound {
        return getPaidSerialized(
                billingService
                        .getBill(billID)
                        .getPaidList()
        );
    }

    public Paid addPaid(PaidDTO paidDTO) throws ResourceNotFound {
        if (paidDTO.getCard().length() != 16) {
            throw new RuntimeException("Card must be 16 digits");
        }

        Paid paid = new Paid();
        Billings billings = billingService.getBill(paidDTO.getBillID());

        BeanUtils.copyProperties(paidDTO, paid);
        paid.setBill(billings);

        return paidRepository.save(paid);
    }

    public void deletePaid(String id) {
        paidRepository.deleteById(UUID.fromString(id));
    }
}
