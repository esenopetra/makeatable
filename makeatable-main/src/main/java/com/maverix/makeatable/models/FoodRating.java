package com.maverix.makeatable.models;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class FoodRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double rating;
    private Long ratingNum;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
