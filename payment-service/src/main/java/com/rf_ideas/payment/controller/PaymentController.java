package com.rf_ideas.payment.controller;

import com.rf_ideas.payment.dto.PaymentRequestDto;
import com.rf_ideas.payment.dto.PaymentResponseDto;
import com.rf_ideas.payment.dto.PaymentStatusUpdateRequest;
import com.rf_ideas.payment.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody @Valid PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto response = paymentService.createPayment(paymentRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/order/{orderId}")
    public ResponseEntity<PaymentResponseDto> getPaymentByOrderId(@PathVariable String orderId) {
        PaymentResponseDto response = paymentService.getPaymentByOrderId(orderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{paymentId}/status")
    public ResponseEntity<PaymentResponseDto> updateStatus(@PathVariable Long paymentId, @RequestBody PaymentStatusUpdateRequest paymentStatusUpdateRequest) {
        PaymentResponseDto response = paymentService.updatePaymentStatus(paymentId, paymentStatusUpdateRequest);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
