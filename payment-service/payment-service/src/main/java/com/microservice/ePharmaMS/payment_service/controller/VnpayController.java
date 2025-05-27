package com.microservice.ePharmaMS.payment_service.controller;

import com.microservice.ePharmaMS.payment_service.DTO.VnpayRequest;
import com.microservice.ePharmaMS.payment_service.service.VnpayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import lombok.AllArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.util.Map;

@RestController
@RequestMapping("/api/vnpay")
@AllArgsConstructor
public class VnpayController {

    private final VnpayService vnpayService;

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody VnpayRequest paymentRequest) {
        System.out.println("Received amount: " + paymentRequest.getAmount());  // Thêm dòng này để debug

        try {
            String paymentUrl = vnpayService.createPayment(paymentRequest);
            return ResponseEntity.ok(paymentUrl);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Đã xảy ra lỗi khi tạo thanh toán!");
        }
    }

    @GetMapping("/return")
    public ResponseEntity<String> returnPayment(@RequestParam Map<String, String> allParams) {
        System.out.println("Params from VNPay return: " + allParams);
        try {
            return vnpayService.validateVnpayResponse(allParams);
        } catch (UnsupportedEncodingException e) {
            // Xử lý lỗi encoding, trả về lỗi cho client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi xử lý mã hóa dữ liệu: " + e.getMessage());
        }
    }
}
