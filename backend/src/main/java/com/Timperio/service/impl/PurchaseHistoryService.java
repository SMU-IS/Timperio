package com.Timperio.service.impl;

import com.Timperio.enums.ChannelType;
import com.Timperio.enums.SalesType;
import com.Timperio.enums.ShippingMethod;
import com.Timperio.models.PurchaseHistory;

public interface PurchaseHistoryService {
    public Iterable<PurchaseHistory> findAll();

    public Iterable<PurchaseHistory> findByCustomerId(Integer id);

    public Iterable<PurchaseHistory> findBySalesType(SalesType salesType);

    public Iterable<PurchaseHistory> findByChannelType(ChannelType channelType);

    public Iterable<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod);
}
