package com.lcwd.user.service.services;

import com.lcwd.user.service.entities.User;

import java.util.List;

public interface UserService {

    //User Operations
    //Create

    User saveUser(User user);

    //Get All users
    List<User> getAllUsers();
    //Get Single User
    User getUser(String userId);
    //Delete user
    void deleteUser(String userId);

    //Update User
    User updateUser(User user,String userId);
}
