package com.maverix.makeatable.dto.Orders;
import com.maverix.makeatable.enums.RoomType;
import lombok.Data;

@Data
public class OrderRequestDTO {
    private String restName;
    private Long seatNum;
    private Long userId;
    private RoomType typeRoom;
}
