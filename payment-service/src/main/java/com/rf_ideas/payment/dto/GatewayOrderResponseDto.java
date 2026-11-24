package com.rf_ideas.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class GatewayOrderResponseDto {
    private String gatewayOrderId;
    private String currency;
    private BigDecimal amount;
    private String status;
    private String gatewayName;
}
