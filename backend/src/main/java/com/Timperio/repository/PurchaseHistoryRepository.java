package com.Timperio.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Timperio.enums.ChannelType;
import com.Timperio.enums.SalesType;
import com.Timperio.enums.ShippingMethod;
import com.Timperio.models.PurchaseHistory;

@Repository
public interface PurchaseHistoryRepository extends CrudRepository<PurchaseHistory, Integer> {
    Iterable<PurchaseHistory> findByCustomerId(Integer customerId);

    Iterable<PurchaseHistory> findBySalesType(SalesType salesType);

    Iterable<PurchaseHistory> findByChannelType(ChannelType channelType);

    Iterable<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod);
}
