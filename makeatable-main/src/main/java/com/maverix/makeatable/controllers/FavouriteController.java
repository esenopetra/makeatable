package com.maverix.makeatable.controllers;

import com.maverix.makeatable.dto.Favourite.FavouriteGetDto;
import com.maverix.makeatable.dto.Favourite.FavouritePostDto;
import com.maverix.makeatable.exceptions.NoFavoritesFoundException;
import com.maverix.makeatable.services.FavouriteService;
import com.maverix.makeatable.util.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("api/favourites")
public class FavouriteController {

    private final FavouriteService favouriteService;

    public FavouriteController(FavouriteService favouriteService) {
        this.favouriteService = favouriteService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response<FavouriteGetDto>> getFavouriteById(@PathVariable Long id) {
        FavouriteGetDto favourite = favouriteService.getFavouriteById(id);
        Response<FavouriteGetDto> response = Response.<FavouriteGetDto>builder()
                .statusCode(HttpStatus.OK.value())
                .status(HttpStatus.OK)
                .message("Favourite retrieved successfully")
                .data(favourite)
                .build();
        return ResponseEntity.ok(response);
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<Response<List<FavouriteGetDto>>> getFavoritesByUserId(@PathVariable Long userId) {
        try {
            List<FavouriteGetDto> favorites = favouriteService.getFavoritesByUserId(userId);

            Response<List<FavouriteGetDto>> response = Response.<List<FavouriteGetDto>>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.OK.value())
                    .status(HttpStatus.OK)
                    .message("Favorites retrieved successfully")
                    .data(favorites)
                    .build();

            return ResponseEntity.ok(response);
        } catch (NoFavoritesFoundException e) {
            Response<List<FavouriteGetDto>> response = Response.<List<FavouriteGetDto>>builder()
                    .timeStamp(LocalDateTime.now())
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .status(HttpStatus.NOT_FOUND)
                    .message("No favorites found for the given user ID.")
                    .build();

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<Response<FavouritePostDto>> createFavourite(@RequestBody FavouritePostDto favouritepostDto) {

        FavouritePostDto createdFavourite = favouriteService.createFavourite(favouritepostDto);

        Response<FavouritePostDto> response = Response.<FavouritePostDto>builder()
                .statusCode(HttpStatus.CREATED.value())
                .status(HttpStatus.CREATED)
                .message("Favourite created successfully")
                .data(createdFavourite)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteFavourite(@PathVariable Long id) {
        favouriteService.deleteFavourite(id);

        Response<Void> response = Response.<Void>builder()
                .statusCode(HttpStatus.NO_CONTENT.value())
                .status(HttpStatus.NO_CONTENT)
                .message("Favourite deleted successfully")
                .build();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
