package com.lcwd.hotel.services;

import com.lcwd.hotel.entities.Hotel;

import java.util.List;

public interface HotelService {

    //create
    Hotel createHotel(Hotel hotel);

    //get user by id
    Hotel getHotelById(String id);

    //get all users
    List<Hotel> getAllHotels();
}
