package com.Timperio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.Timperio.models.PurchaseHistory;

import com.Timperio.enums.*;

@Repository
public interface PurchaseHistoryRepository extends CrudRepository<PurchaseHistory, Integer> {
    Iterable<PurchaseHistory> findByCustomerId(Integer customerId);

    Iterable<PurchaseHistory> findBySalesType(SalesType salesType);

    Iterable<PurchaseHistory> findByChannelType(ChannelType channelType);

    Iterable<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod);
}
