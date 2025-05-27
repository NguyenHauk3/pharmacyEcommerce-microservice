package com.microservice.ePharmaMS.order_service.DTO;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class OrderReportDTO {
    private LocalDate date;  // ngày thống kê
    private Long orderCount; // số đơn hàng
    private BigDecimal totalRevenue; // tổng doanh thu

    // constructor, getter, setter
    public OrderReportDTO(LocalDate date, Long orderCount, BigDecimal totalRevenue) {
        this.date = date;
        this.orderCount = orderCount;
        this.totalRevenue = totalRevenue;
    }
    // getters và setters
}
