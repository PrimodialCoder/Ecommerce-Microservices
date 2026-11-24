package com.rf_ideas.payment.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.rf_ideas.payment.dto.GatewayOrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class RazorpayPaymentGateway implements PaymentGateway{

    private final RazorpayClient razorpayClient;

    @Override
    public GatewayOrderResponseDto createOrder(String orderId, BigDecimal amount) {
        try{
            JSONObject options = new JSONObject();
            options.put("amount", amount.multiply(BigDecimal.valueOf(100)));
            options.put("currency", "INR");
            options.put("receipt", orderId);
            Order order = razorpayClient.orders.create(options);
            return new GatewayOrderResponseDto(order.get("id"), order.get("amount"), order.get("currency"), order.get("status"), "RAZORPAY");
        }catch (RazorpayException ex){
            throw new IllegalStateException("Razorpay order creation failed : " + ex.getMessage(), ex);
        }
    }
}
