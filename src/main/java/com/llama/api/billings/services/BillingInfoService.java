package com.llama.api.billings.services;

import com.llama.api.billings.dto.BillingInfoDTO;
import com.llama.api.billings.models.BillingInfo;
import com.llama.api.billings.repository.BillingInfoRepository;
import com.llama.api.billings.repository.BillingRepository;
import com.llama.api.billings.serializer.BillingInfoSerialized;
import com.llama.api.exceptions.ResourceNotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BillingInfoService {
    @Autowired
    BillingInfoRepository billingInfoRepository;

    @Autowired
    BillingRepository billingRepository;

    public BillingInfo getBillingInfo(String id) throws ResourceNotFound {
        return billingInfoRepository
                .findById(
                        UUID.fromString(id)
                ).orElseThrow(
                        () -> new ResourceNotFound("Info not found")
                );
    }

    public BillingInfoSerialized getBillingInfoSerialized(String id) throws ResourceNotFound {
        return BillingInfoSerialized.serialize(getBillingInfo(id));
    }

    public String getBillID(String id) throws ResourceNotFound {
        return billingRepository
                .findByBillingInfo(
                        getBillingInfo(id)
                )
                .getId()
                .toString();
    }

    public BillingInfo createBillInfo(BillingInfoDTO billingInfo) {
        BillingInfo billingInfoModel = new BillingInfo();
        BeanUtils.copyProperties(billingInfo, billingInfoModel);
        return billingInfoRepository.save(billingInfoModel);
    }

    public BillingInfo updateBillInfo(String id, BillingInfoDTO billingInfo) throws ResourceNotFound {
        BillingInfo billingInfoModel = getBillingInfo(id);
        BeanUtils.copyProperties(billingInfo, billingInfoModel);
        return billingInfoRepository.save(billingInfoModel);
    }

    public void deleteBillInfo(String id) {
        billingInfoRepository.deleteById(UUID.fromString(id));
    }
}
