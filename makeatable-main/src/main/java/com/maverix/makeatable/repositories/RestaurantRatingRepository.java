package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.RestaurantRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRatingRepository extends JpaRepository<RestaurantRating, Long> {
}
