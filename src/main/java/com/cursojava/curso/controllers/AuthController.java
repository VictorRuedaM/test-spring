package com.cursojava.curso.controllers;


import com.cursojava.curso.dao.UserDao;
import com.cursojava.curso.models.User;
import com.cursojava.curso.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping(value = "/login")
    public String login(@RequestBody User user) {

        User result = userDao.loginUser(user);


        if(result != null) {

            String token = jwtUtil.create(
                    String.valueOf(result.getId()),
                    result.getEmail()
                    );
            return token;
        }else{
            return "Login failed";
        }
    }
}
