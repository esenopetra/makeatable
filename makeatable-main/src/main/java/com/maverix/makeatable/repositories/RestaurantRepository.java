package com.maverix.makeatable.repositories;

import com.maverix.makeatable.enums.FoodCategory;
import com.maverix.makeatable.enums.RestStatus;
import com.maverix.makeatable.models.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends JpaRepository<Restaurant,Long> {
    Restaurant findByFullName(String fullName);

    @Query("SELECT r FROM Restaurant r ORDER BY (SELECT AVG(rr.rating) FROM r.ratings rr) DESC")
    List<Restaurant> findTop5ByOrderByAverageRatingDesc();

    Optional<Restaurant> findByUser_Id(Long userId);

    Restaurant findByUserId(Long userId);
   @Query("SELECT s FROM Restaurant s WHERE " +
            "LOWER(s.fullName) LIKE CONCAT('%', LOWER(:query), '%')" +
            "OR LOWER(s.description) LIKE CONCAT('%', LOWER(:query), '%')" +
            "OR LOWER(s.location) LIKE CONCAT('%', LOWER(:query), '%')")
    List<Restaurant> searchRestaurant(String query);
    @Query("SELECT r FROM Restaurant r " +
            "WHERE LOWER(r.location) LIKE CONCAT('%', LOWER(:location), '%') " +
            "AND r.foodType = :foodType")
    List<Restaurant> filterRestaurantsByLocationAndFoodType(
            @Param("location") String location,
            @Param("foodType") FoodCategory foodType);

    List<Restaurant> findByStatus(RestStatus status);
}
