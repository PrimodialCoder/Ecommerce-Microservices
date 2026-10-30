package com.rf_ideas.payment.dto;

import com.rf_ideas.payment.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {
    private Long paymentId;
    private String orderId;
    private BigDecimal amount;
    private PaymentStatus paymentStatus;
    private String transactionId;
}
