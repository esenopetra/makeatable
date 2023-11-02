package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.FoodRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRatingRepository extends JpaRepository<FoodRating,Long> {
}
