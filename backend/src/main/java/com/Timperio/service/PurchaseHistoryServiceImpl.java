package com.Timperio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Timperio.enums.ChannelType;
import com.Timperio.enums.SalesType;
import com.Timperio.enums.ShippingMethod;
import com.Timperio.models.PurchaseHistory;
import com.Timperio.repository.PurchaseHistoryRepository;
import com.Timperio.service.impl.PurchaseHistoryService;

@Service
public class PurchaseHistoryServiceImpl implements PurchaseHistoryService {

    @Autowired
    private PurchaseHistoryRepository purchaseHistoryRepository;

    public Iterable<PurchaseHistory> findAll() {
        return purchaseHistoryRepository.findAll();
    }

    public Iterable<PurchaseHistory> findByCustomerId(Integer id) {
        return purchaseHistoryRepository.findByCustomerId(id);
    }

    public Iterable<PurchaseHistory> findBySalesType(SalesType salesType) {
        return purchaseHistoryRepository.findBySalesType(salesType);
    }

    public Iterable<PurchaseHistory> findByChannelType(ChannelType channelType) {
        return purchaseHistoryRepository.findByChannelType(channelType);
    }

    public Iterable<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod) {
        return purchaseHistoryRepository.findByShippingMethod(shippingMethod);
    }
}
