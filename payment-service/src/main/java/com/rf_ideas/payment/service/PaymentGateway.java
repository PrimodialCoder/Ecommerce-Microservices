package com.rf_ideas.payment.service;

import com.razorpay.RazorpayException;
import com.rf_ideas.payment.dto.GatewayOrderResponseDto;

import java.math.BigDecimal;

public interface PaymentGateway {
    GatewayOrderResponseDto createOrder(String orderId, BigDecimal amount) throws RazorpayException;
}
