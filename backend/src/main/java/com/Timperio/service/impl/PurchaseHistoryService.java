package com.Timperio.service.impl;

import com.Timperio.models.PurchaseHistory;

import com.Timperio.enums.SalesType;
import com.Timperio.enums.ShippingMethod;
import com.Timperio.enums.ChannelType;

public interface PurchaseHistoryService {
    public Iterable<PurchaseHistory> findAll();

    public Iterable<PurchaseHistory> findByCustomerId(Integer id);

    public Iterable<PurchaseHistory> findBySalesType(SalesType salesType);

    public Iterable<PurchaseHistory> findByChannelType(ChannelType channelType);

    public Iterable<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod);
}
