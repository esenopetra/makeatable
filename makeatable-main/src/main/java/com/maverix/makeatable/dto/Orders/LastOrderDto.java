package com.maverix.makeatable.dto.Orders;
import com.maverix.makeatable.enums.RoomType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LastOrderDto {
    private Long id;
    private String location;
    private LocalDateTime dateTime;
    private RoomType roomType;
}
