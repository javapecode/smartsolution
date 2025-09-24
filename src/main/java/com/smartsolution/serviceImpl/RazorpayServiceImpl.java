package com.smartsolution.serviceImpl;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.api.client.util.Value;
import com.razorpay.RazorpayClient;

@Service
public class RazorpayServiceImpl {
	private RazorpayClient razorpayClient;

	@Value("${payment.gateway.secretKey}")
	private String secretKey;

	@Value("${payment.gateway.publishableKey}")
	private String publishableKey;

	public RazorpayServiceImpl() {
		try {
			razorpayClient = new RazorpayClient(secretKey, publishableKey);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String createOrder(int amount) {
		System.out.println("call payment service method-----!!");
		JSONObject options = new JSONObject(); // Prepare order options
		options.put("amount", amount * 100); // Convert amount to paise (Razorpay accepts only paise)
		options.put("currency", "INR"); // Specify currency (INR)
		options.put("receipt", "order_rcptid_11"); // Custom receipt identifier for tracking orders
		options.put("payment_capture", 1); // Automatically capture payments
		try {
			return razorpayClient.orders.create(options).toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;

		}
	}
//	public boolean verifyPayment(String orderId, String paymentId, String razorpaySignature) {
//		  String payload = orderId + '|' + paymentId;
//		  try {
//		    return Utils.verifySignature(payload, razorpaySignature, razorpayConfig.getSecret());
//		  } catch (Exception e) {
//		    e.printStackTrace();
//		    return false;
//		  }
//		}
}
