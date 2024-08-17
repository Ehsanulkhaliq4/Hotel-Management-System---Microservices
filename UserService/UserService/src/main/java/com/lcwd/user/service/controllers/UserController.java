package com.lcwd.user.service.controllers;

import com.lcwd.user.service.entities.User;
import com.lcwd.user.service.services.UserService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    Logger logger = LoggerFactory.getLogger(UserController.class);
    //create
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user)
    {
        User user1 = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user1);
    }
    //get single user
//    int retryCount=1;
    @GetMapping("/{userId}")
//    @CircuitBreaker(name="ratingHotelBreaker",fallbackMethod = "ratingHotelFallback")
//    @Retry(name="ratingHotelService",fallbackMethod = "ratingHotelFallback")
    @RateLimiter(name = "userRateLimiter",fallbackMethod = "ratingHotelFallback")
    public ResponseEntity<User> getUserById(@PathVariable String userId)
    {
//        logger.info("Retry Count: {}"+retryCount);
//        retryCount++;
        User user = userService.getUser(userId);
        return ResponseEntity.ok(user);
    }
    //creating fallback method for circuit breaker
    public ResponseEntity<User> ratingHotelFallback(String userId,Exception ex)
    {
//        logger.info("UserService is down due to development reasons:"+ex.getMessage());
        ex.printStackTrace();
        User user = User.builder().email("dummy@gmail.com")
                .name("Dummy")
                .about("This user is created for dummy beacuse some services are down!!!")
                .userId("153435")
                .build();
        return new ResponseEntity<>(user,HttpStatus.BAD_REQUEST);
    }
    //Get all users
    @GetMapping("/")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }
}
