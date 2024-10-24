package com.Timperio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.stream.StreamSupport;

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

    @Override
    public Iterable<PurchaseHistory> findAll() {
        return purchaseHistoryRepository.findAll();
    }

    @Override
    public Iterable<PurchaseHistory> findByCustomerId(Integer id) {
        return purchaseHistoryRepository.findByCustomer_CustomerId(id);
    }

    @Override
    public Iterable<PurchaseHistory> findBySalesType(SalesType salesType) {
        return purchaseHistoryRepository.findBySalesType(salesType);
    }

    @Override
    public Iterable<PurchaseHistory> findByChannelType(ChannelType channelType) {
        return purchaseHistoryRepository.findByChannelType(channelType);
    }

    @Override
    public Iterable<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod) {
        return purchaseHistoryRepository.findByShippingMethod(shippingMethod);
    }

    @Override
    public double getSalesTotal(Iterable<PurchaseHistory> purchaseHistoryList){
        double salesSum = 0.0;
        for (PurchaseHistory purchaseHistory : purchaseHistoryList) {
            salesSum += purchaseHistory.getTotalPrice();
        }
        return salesSum;
    }
    @Override
    public double getAvgOrderValue(Iterable<PurchaseHistory> purchaseHistoryList){
        double salesSum = 0.0;
        int count = 0;
        for (PurchaseHistory purchaseHistory : purchaseHistoryList) {
            salesSum += purchaseHistory.getTotalPrice();
            count += 1;
        }
        if (count == 0) {
            return 0.0;
        }
        return salesSum / count;
    }
    @Override
    public int getSalesCount(Iterable<PurchaseHistory> purchaseHistoryList){
        return (int) StreamSupport.stream(purchaseHistoryList.spliterator(), false).count();
    }
}
