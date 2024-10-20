package com.Timperio.models;

import lombok.Data;

import com.Timperio.enums.*;

import org.hibernate.annotations.JdbcType;
import org.hibernate.dialect.PostgreSQLEnumJdbcType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "purchase_history")
@Data
public class PurchaseHistory {
    @Id
    @Column(name = "sales_id", nullable = false, unique = true)
    private Integer salesId;

    @Column(name = "sales_date", nullable = false)
    private String salesDate;

    @Column(name = "sales_type", nullable = true)
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class) // Custom mapping for PostgreSQL ENUM types !MUST HAVE
    private SalesType salesType;

    @Column(name = "channel_type", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class) // Custom mapping for PostgreSQL ENUM types !MUST HAVE
    private ChannelType channelType;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "zip_code", nullable = false)
    private Integer zipCode;

    @Column(name = "shipping_method", nullable = false)
    @Enumerated(EnumType.STRING)
    @JdbcType(PostgreSQLEnumJdbcType.class) // Custom mapping for PostgreSQL ENUM types !MUST HAVE
    private ShippingMethod shippingMethod;

    @Column(name = "product", nullable = false)
    private String product;

    @Column(name = "unit_price", nullable = false)
    private Double unitPrice;

    @Column(name = "variant", nullable = false)
    private Integer variant;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    @Column(name = "total_price", nullable = false)
    private Double totalPrice;
}
