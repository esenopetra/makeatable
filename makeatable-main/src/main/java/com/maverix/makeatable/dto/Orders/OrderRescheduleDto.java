package com.maverix.makeatable.dto.Orders;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class OrderRescheduleDto {
    private LocalDateTime rescheduledDateTime;
}
