package com.microservice.ePharmaMS.payment_service.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import com.microservice.ePharmaMS.payment_service.DTO.VnpayRequest;
import com.microservice.ePharmaMS.payment_service.config.VnpayConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class VnpayService {
    public String createPayment(VnpayRequest paymentRequest) throws UnsupportedEncodingException {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String orderType = "other";

        long amount = 0;
//        try {
//            amount = Long.parseLong(paymentRequest.getAmount()) * 100;
//        } catch (NumberFormatException e) {
//            throw new IllegalArgumentException("Số tiền không hợp lệ");
//        }
        try {
            double parsedAmount = Double.parseDouble(paymentRequest.getAmount());
            amount = (long) (parsedAmount * 100);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số tiền không hợp lệ");
        }

        String bankCode = "NCB";
        String vnp_TxnRef = VnpayConfig.getRandomNumber(8);
        String vnp_IpAddr = "127.0.0.1";
        String vnp_TmnCode = VnpayConfig.vnp_TmnCode;

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");

        vnp_Params.put("vnp_BankCode", bankCode);
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
        vnp_Params.put("vnp_OrderType", orderType);
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VnpayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
//            if ((fieldValue != null) && (fieldValue.length() > 0)) {
//                hashData.append(fieldName).append('=')
//                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()))
//                        .append('=')
//                        .append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
//                query.append('&');
//                hashData.append('&');
//            }
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                hashData.append(fieldName).append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.name()));
                query.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.name()))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.name()));
                query.append('&');
                hashData.append('&');
            }
        }

        if (query.length() > 0)
            query.setLength(query.length() - 1);
        if (hashData.length() > 0)
            hashData.setLength(hashData.length() - 1);

        String vnp_SecureHash = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());
        query.append("&vnp_SecureHash=").append(vnp_SecureHash);
        return VnpayConfig.vnp_PayUrl + "?" + query;
    }

    public ResponseEntity<String> handlePaymentReturn(String responseCode) {
        if ("00".equals(responseCode)) {
            return ResponseEntity.ok("Thanh toán thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán thất bại! Mã lỗi: " + responseCode);
        }
    }

    public ResponseEntity<String> validateVnpayResponse(Map<String, String> params) throws UnsupportedEncodingException {
        String vnp_SecureHash = params.get("vnp_SecureHash");
        if (vnp_SecureHash == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thiếu chữ ký xác minh!");
        }

        // Bỏ vnp_SecureHash & vnp_SecureHashType ra khỏi danh sách
        Map<String, String> sortedParams = new HashMap<>(params);
        sortedParams.remove("vnp_SecureHash");
        sortedParams.remove("vnp_SecureHashType");

        // Sắp xếp tham số theo key
        List<String> fieldNames = new ArrayList<>(sortedParams.keySet());
        Collections.sort(fieldNames);

        // Build chuỗi dữ liệu để hash lại
        StringBuilder hashData = new StringBuilder();
        for (String fieldName : fieldNames) {
            String fieldValue = sortedParams.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                hashData.append(URLEncoder.encode(fieldName, StandardCharsets.UTF_8.name()))
                        .append('=')
                        .append(URLEncoder.encode(fieldValue, StandardCharsets.UTF_8.name()))
                        .append('&');
            }
        }
        if (hashData.length() > 0) {
            hashData.setLength(hashData.length() - 1); // remove trailing '&'
        }

        // Hash lại bằng secretKey
        String myChecksum = VnpayConfig.hmacSHA512(VnpayConfig.secretKey, hashData.toString());

        if (!myChecksum.equals(vnp_SecureHash)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Sai chữ ký!");
        }

        String responseCode = params.get("vnp_ResponseCode");
        if ("00".equals(responseCode)) {
            return ResponseEntity.ok("Thanh toán thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán thất bại! Mã lỗi: " + responseCode);
        }
    }
}
