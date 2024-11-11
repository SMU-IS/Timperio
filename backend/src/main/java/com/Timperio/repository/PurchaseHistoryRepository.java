package com.Timperio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
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
            + "FROM PurchaseHistory p "
            + "WHERE (:customerId IS NULL OR p.customer.customerId = :#{#customerId}) "
            + "AND (:salesType IS NULL OR p.salesType = :#{#salesType})"
            + "AND ((cast(:salesDate as DATE) is NULL) OR p.salesDate = :#{#salesDate})")
    List<PurchaseHistoryDto> findAllFilteredPurchaseHistories(
            @Param("customerId") Integer customerId,
            @Param("salesType") SalesType salesType,
            @Param("salesDate") LocalDate salesDate);

    List<PurchaseHistory> findByCustomer_CustomerId(Integer customerId);

    List<PurchaseHistory> findBySalesType(SalesType salesType);

    List<PurchaseHistory> findByChannelType(ChannelType channelType);

    List<PurchaseHistory> findByShippingMethod(ShippingMethod shippingMethod);
}
