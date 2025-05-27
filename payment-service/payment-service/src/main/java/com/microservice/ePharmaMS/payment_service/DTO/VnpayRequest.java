package com.microservice.ePharmaMS.payment_service.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class VnpayRequest {
    private String amount;
}
