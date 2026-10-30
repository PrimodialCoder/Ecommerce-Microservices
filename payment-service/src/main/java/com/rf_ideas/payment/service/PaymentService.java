package com.rf_ideas.payment.service;

import com.rf_ideas.payment.dto.PaymentRequestDto;
import com.rf_ideas.payment.dto.PaymentResponseDto;
import com.rf_ideas.payment.dto.PaymentStatusUpdateRequest;

public interface PaymentService {
    PaymentResponseDto createPayment(PaymentRequestDto requestDto);
    PaymentResponseDto getPaymentByOrderId(String orderId);
    PaymentResponseDto updatePaymentStatus(Long paymentId, PaymentStatusUpdateRequest paymentStatusUpdateRequest);
}
