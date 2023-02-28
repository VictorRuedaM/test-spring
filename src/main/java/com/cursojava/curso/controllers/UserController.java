package com.cursojava.curso.controllers;


import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import com.cursojava.curso.utils.JwtUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping(value = "/api")
public class UserController {

    @Autowired
    private UserDao userDao;

    @Autowired
    private JwtUtil jwtUtil;

    @GetMapping("/users")
    public List<User> getUsers(@RequestHeader(value = "Authorization") String token){

        String userId = jwtUtil.getKey(token);

        if(userId == null){
            return new ArrayList<>();
        }

        return userDao.getUsers();
    }



    @PostMapping(value = "/users")
    public void createUser(@RequestBody User user){

        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
        String hash = argon2.hash(10, 1024, 2, user.getPassword());
        System.out.println("the hash is " + hash);
        user.setPassword(hash);
        userDao.createUser(user);


    }



    @DeleteMapping(value = "/users/{id}")
    public void deleteUser(@PathVariable int id, @RequestHeader(value = "Authorization") String token){
        String userId = jwtUtil.getKey(token);
        if(userId != null){
            System.out.println(id);
            userDao.deleteUser(id);

        }

    }
}
