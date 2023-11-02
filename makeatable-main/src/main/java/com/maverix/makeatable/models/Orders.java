package com.maverix.makeatable.models;

import com.maverix.makeatable.enums.RoomType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
@Entity
public class Orders {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @CreatedBy
    private User createdByUser;

    @ManyToOne(optional = true)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private LocalDateTime fromDateTime;
    private LocalDateTime toDateTime;

    private Long seatNum;

    @Enumerated(EnumType.STRING)
    private RoomType typeRoom;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
