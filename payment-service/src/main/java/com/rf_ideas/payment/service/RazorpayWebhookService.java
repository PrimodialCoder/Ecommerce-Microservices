package com.rf_ideas.payment.service;

import com.razorpay.Utils;
import com.rf_ideas.payment.cleint.InventoryClient;
import com.rf_ideas.payment.cleint.OrderClient;
import com.rf_ideas.payment.enums.PaymentStatus;
import com.rf_ideas.payment.model.Payment;
import com.rf_ideas.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RazorpayWebhookService {
    private final PaymentRepository paymentRepository;
    private final OrderClient orderClient;
    private final InventoryClient inventoryClient;

    @Value("${razorpay.webhook-secret}")
    private String webhookSecret;

    public void processWebhook(String signature, String payload){
        verifySignature(signature, payload);
        JSONObject event = new JSONObject(payload);
        String eventType = event.getString("event");
        log.info("Received RazorpayWebhook Event: " + eventType);
        switch (eventType) {
            case "payment.captured" -> handlePaymentSuccess(event);
            case "payment.failed" -> handlePaymentFailure(event);
            default -> log.warn("Unhandled RazorpayWebhook Event: " + eventType);
        }



    }

    private void verifySignature(String signature, String payload) {
        // Implementation for verifying the webhook signature
        try{
            Utils.verifyWebhookSignature(payload, signature, webhookSecret);
        } catch (Exception e) {
            log.error("Error verifying webhook signature", e);
            throw new SecurityException("Invalid razorpay webhook signature");
        }
    }

    private void handlePaymentSuccess(JSONObject event) {
        JSONObject eventObject = extractPaymentEntity(event);
        String razorpayOrderId = eventObject.getString("order_id");
        String razorpayPayemntId = eventObject.getString("id");
        Payment payment = paymentRepository.findByTransactionId(razorpayOrderId).orElseThrow(() -> new IllegalStateException("Payment not found for orderId: " + razorpayPayemntId));
        payment.setPaymentStatus(PaymentStatus.SUCCESS);
        payment.setGatewayPaymentId(razorpayPayemntId);
        orderClient.confirmOrder(payment.getOrderId());
        inventoryClient.commitInventory(payment.getOrderId());
    }

    private void handlePaymentFailure(JSONObject event) {
        JSONObject eventObject = extractPaymentEntity(event);
        String razorpayOrderId = eventObject.getString("order_id");
        Payment payment = paymentRepository.findByTransactionId(razorpayOrderId).orElseThrow(() -> new IllegalStateException("Payment not found for orderId: " + razorpayOrderId));
        payment.setPaymentStatus(PaymentStatus.FAILED);
        orderClient.failOrder(payment.getOrderId());
        inventoryClient.rollbackInventory(payment.getOrderId());
    }

    private JSONObject extractPaymentEntity(JSONObject event){
        return event.getJSONObject("payload").getJSONObject("payment").getJSONObject("entity");
    }
}
