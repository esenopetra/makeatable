package com.maverix.makeatable.models;

import com.maverix.makeatable.enums.FoodCategory;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private FoodCategory category;

    private String subCategory;

    private Double price;
    @OneToMany(mappedBy = "food")
    private List<FoodRating> ratings;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    private String imageUrl;

    private Long calories;

    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
    public Double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        double totalRating = 0.0;
        for (FoodRating rating : ratings) {
            totalRating += rating.getRating();
        }

        return totalRating / ratings.size();
    }
}
