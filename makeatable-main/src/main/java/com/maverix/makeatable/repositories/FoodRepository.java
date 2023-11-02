package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    @Query("SELECT f FROM Food f ORDER BY (SELECT AVG(r.rating) FROM f.ratings r) DESC")
    List<Food> findTop5ByOrderByAverageRatingDesc();
}
