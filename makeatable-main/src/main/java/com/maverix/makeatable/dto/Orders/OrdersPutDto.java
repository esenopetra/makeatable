package com.maverix.makeatable.dto.Orders;
import com.maverix.makeatable.enums.RoomType;
import lombok.Data;
import java.time.LocalDateTime;
@Data
public class OrdersPutDto {
    private Long userId;
    private Long restaurantId;
    private LocalDateTime dateTime;
    private Long seatNum;
    private RoomType roomType;
}
