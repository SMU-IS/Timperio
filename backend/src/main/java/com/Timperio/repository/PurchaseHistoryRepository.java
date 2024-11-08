package com.Timperio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Timperio.dto.PurchaseHistoryDto;
import com.Timperio.enums.ChannelType;
import com.Timperio.enums.SalesType;
import com.Timperio.enums.ShippingMethod;
import com.Timperio.models.PurchaseHistory;

@Repository
public interface PurchaseHistoryRepository extends CrudRepository<PurchaseHistory, Integer> {

    List<PurchaseHistory> findAll();

    @Query("SELECT new com.Timperio.dto.PurchaseHistoryDto(p.customer.customerId, p.salesType, p.totalPrice, p.salesDate) "
            +
            "FROM PurchaseHistory p")
    List<PurchaseHistoryDto> findAllFilteredPurchaseHistories();

    List<PurchaseHistory> findByCustomer_CustomerId(Integer customerId);

    List<PurchaseHistory> findBySalesType(SalesType salesType);

    List<PurchaseHistory> findByChannelType(ChannelType channelType);

    List<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod);
}
