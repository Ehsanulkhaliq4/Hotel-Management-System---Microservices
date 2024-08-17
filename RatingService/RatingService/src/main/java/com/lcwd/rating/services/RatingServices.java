package com.lcwd.rating.services;

import com.lcwd.rating.entities.Rating;

import java.util.List;

public interface RatingServices {

    //create
    Rating createRating(Rating rating);

    //get All rating
    List<Rating> getAllRatings();

    //get all by user id
    List<Rating> getRatingByUserId(String userId);

    //get all by hotel id
    List<Rating> getRatingByHotelId(String hotelId);
}
