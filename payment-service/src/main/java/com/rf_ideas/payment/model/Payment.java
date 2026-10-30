package com.rf_ideas.payment.model;

import com.rf_ideas.payment.enums.PaymentMethod;
import com.rf_ideas.payment.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "paymets", indexes = {
        @Index(columnList = "orderId"),
        @Index(columnList = "transactionId")
})
@Builder
public class Payment extends BaseModel {
    @Column(nullable = false)
    private String orderId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount; //for amount this is exact storage for number

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Column(unique = true, nullable = false)
    private String transactionId;

    private String gatewayPaymentId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    void onCreate(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    void onUpdate(){
        this.updatedAt = LocalDateTime.now();
    }
}
