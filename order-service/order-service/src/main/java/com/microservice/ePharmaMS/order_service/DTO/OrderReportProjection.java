package com.microservice.ePharmaMS.order_service.DTO;
import java.math.BigDecimal;
import java.time.LocalDate;

public interface OrderReportProjection {
    LocalDate getDate();
    Long getOrderCount();
    BigDecimal getTotalRevenue();
}
