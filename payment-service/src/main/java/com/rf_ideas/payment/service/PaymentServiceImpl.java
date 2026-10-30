package com.rf_ideas.payment.service;

import com.rf_ideas.payment.dto.PaymentRequestDto;
import com.rf_ideas.payment.dto.PaymentResponseDto;
import com.rf_ideas.payment.dto.PaymentStatusUpdateRequest;
import com.rf_ideas.payment.enums.PaymentMethod;
import com.rf_ideas.payment.enums.PaymentStatus;
import com.rf_ideas.payment.exception.PaymentNotFoundException;
import com.rf_ideas.payment.model.Payment;
import com.rf_ideas.payment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponseDto createPayment(PaymentRequestDto requestDto) {
        Payment payment = Payment.builder()
                .amount(requestDto.getAmount())
                .paymentMethod(PaymentMethod.valueOf(requestDto.getPaymentMethod()))
                .paymentStatus(PaymentStatus.PENDING)
                .transactionId(TransactionIdGenerator.generate())
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return mapToResponse(savedPayment);
    }

    @Override
    public PaymentResponseDto getPaymentByOrderId(String orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId).orElseThrow(() -> new PaymentNotFoundException("Payment not found for orderId: " + orderId));
        return mapToResponse(payment);
    }

    @Override
    public PaymentResponseDto updatePaymentStatus(Long paymentId, PaymentStatusUpdateRequest paymentStatusUpdateRequest) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new PaymentNotFoundException("Payment not found for paymentId: " + paymentId));
        payment.setPaymentStatus(paymentStatusUpdateRequest.getPaymentStatus());

    }

    private PaymentResponseDto mapToResponse(Payment payment) {
        PaymentResponseDto responseDto = new PaymentResponseDto();
        responseDto.setPaymentId(payment.getId());
        responseDto.setAmount(payment.getAmount());
        responseDto.setPaymentStatus(payment.getPaymentStatus());
        responseDto.setTransactionId(payment.getTransactionId());
        responseDto.setOrderId(payment.getOrderId());
        return responseDto;
    }
}
