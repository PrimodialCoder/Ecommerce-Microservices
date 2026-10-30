package com.rf_ideas.payment.mapper;

import com.rf_ideas.payment.dto.PaymentRequestDto;
import com.rf_ideas.payment.dto.PaymentResponseDto;
import com.rf_ideas.payment.model.Payment;

public class DtoMapper {
    public static PaymentResponseDto toDto(Payment payment){

    }

    public static Payment toEntity(PaymentRequestDto requestDto) {
        Payment payment = new Payment();
        payment.setAmount(requestDto.getAmount());
    }
}
