package com.maverix.makeatable.services;
import com.maverix.makeatable.dto.Favourite.FavouriteGetDto;
import com.maverix.makeatable.dto.Favourite.FavouritePostDto;
import com.maverix.makeatable.exceptions.NoFavoritesFoundException;
import com.maverix.makeatable.models.Favourite;
import com.maverix.makeatable.repositories.FavouriteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavouriteService {

    private final FavouriteRepository favouriteRepository;

    public FavouriteService(FavouriteRepository favouriteRepository) {
        this.favouriteRepository = favouriteRepository;
    }

    public List<FavouriteGetDto> getAllFavourites() {
        List<Favourite> favourites = favouriteRepository.findAll();
        return favourites.stream()
                .map(this::convertToFavouritegetDto)
                .collect(Collectors.toList());
    }

    public FavouriteGetDto getFavouriteById(Long id) {
        Optional<Favourite> optionalFavourite = favouriteRepository.findById(id);
        return optionalFavourite.map(this::convertToFavouritegetDto).orElse(null);
    }

    public FavouritePostDto createFavourite(FavouritePostDto favouritepostDto) {
        Favourite favourite = convertToEntity(favouritepostDto);
        Favourite savedFavourite = favouriteRepository.save(favourite);
        return convertToFavouritepostDto(savedFavourite);
    }


    public void deleteFavourite(Long id) {
        favouriteRepository.deleteById(id);
    }
    private FavouriteGetDto convertToFavouritegetDto(Favourite favourite) {
        FavouriteGetDto favouritegetDto = new FavouriteGetDto();
        BeanUtils.copyProperties(favourite, favouritegetDto);
        return favouritegetDto;
    }

    public List<FavouriteGetDto> getFavoritesByUserId(Long userId) {
        List<Favourite> favourites = favouriteRepository.findByCreatedByUser_Id(userId);

        if (favourites.isEmpty()) {
            throw new NoFavoritesFoundException("No favorites found for the given user ID.");
        }

        return favourites.stream()
                .map(this::convertToFavouritegetDto)
                .collect(Collectors.toList());
    }



    private FavouritePostDto convertToFavouritepostDto(Favourite favourite) {
        FavouritePostDto favouritepostDto = new FavouritePostDto();
        BeanUtils.copyProperties(favourite, favouritepostDto);
        return favouritepostDto;
    }

    private Favourite convertToEntity(FavouritePostDto favouritepostDto) {
        Favourite favourite = new Favourite();
        BeanUtils.copyProperties(favouritepostDto, favourite);
        favourite.setCreatedDate(LocalDateTime.now());
        favourite.setLastModifiedDate(LocalDateTime.now());
        return favourite;
    }

}
