package com.maverix.makeatable.repositories;

import com.maverix.makeatable.models.Favourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavouriteRepository extends JpaRepository<Favourite,Long> {
    List<Favourite> findByCreatedByUser_Id(Long userId);
}
