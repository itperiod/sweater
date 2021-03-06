package com.example.Spring.controller;

import com.example.Spring.domain.Role;
import com.example.Spring.domain.User;
import com.example.Spring.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "Такой миньён уже есть!");
            return "registration";
        }

        user.setActive(true);
        if (userRepo.findAll().isEmpty())
        {
            user.setRoles(Collections.singleton(Role.ADMIN));
        }
        else
            {
                user.setRoles(Collections.singleton(Role.USER));
            }
        userRepo.save(user);

        return "redirect:/login";
    }


}
