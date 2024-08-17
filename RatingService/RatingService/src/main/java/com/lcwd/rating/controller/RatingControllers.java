package com.lcwd.rating.controller;

import com.lcwd.rating.entities.Rating;
import com.lcwd.rating.services.RatingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
public class RatingControllers {

    @Autowired
    private RatingServices ratingServices;
    //Create Rating
    @PreAuthorize(value = "hasAuthority('Admin')")
    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(ratingServices.createRating(rating));
    }
    //get All

    @GetMapping
    public ResponseEntity<List<Rating>> getRatings()
    {
        return ResponseEntity.status(HttpStatus.OK).body(ratingServices.getAllRatings());
    }
    //get ratings by userId
    @PreAuthorize(value = "hasAuthority('SCOPE_internal') || hasAuthority('Admin')")
    @GetMapping("/users/{userId}")
    public ResponseEntity<List<Rating>> getRatingsByUserId(@PathVariable("userId") String userId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ratingServices.getRatingByUserId(userId));
    }

    //get rating by Hotelid
    @GetMapping("/hotels/{hotelId}")
    public ResponseEntity<List<Rating>> getRatingsByHotelId(@PathVariable("hotelId") String hotelId)
    {
        return ResponseEntity.status(HttpStatus.OK).body(ratingServices.getRatingByHotelId(hotelId));
    }

}
