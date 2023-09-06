package com.Database.Project.Controller;

import com.Database.Project.Helper.JwtUtils;
import com.Database.Project.Model.User;
import com.Database.Project.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private UserService userService;
    @GetMapping("/hello")
    @PreAuthorize("hasRole('USER')")
    public String hello(){
        return "Hi! This is 2 Step Authentication.";
    }
    @GetMapping("/login")
    @PreAuthorize("hasRole('ADMIN')")
    public String login(){
        return "Hi! This is Login.";
    }
    @PostMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        if (user.getName() == null || user.getName().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Invalid input
        }
        User createdUser = userService.createUser(user);
        return ResponseEntity.ok(createdUser);
    }
    @PutMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        if (!userService.existsUser(userId)) {
            return ResponseEntity.notFound().build(); // User doesn't exist
        }
        if (user.getName() == null || user.getName().isEmpty() ||
                user.getPassword() == null || user.getPassword().isEmpty()) {
            return ResponseEntity.badRequest().build(); // Invalid input
        }
        user.setId(userId);
        User updatedUser = userService.updateUser(user);
        return ResponseEntity.ok(updatedUser);
    }
    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        User user = userService.getUserById(userId);
        if (user == null) {
            return ResponseEntity.notFound().build(); // User not found
        }
        return ResponseEntity.ok(user);
    }
    @DeleteMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        if (!userService.existsUser(userId)) {
            return ResponseEntity.notFound().build(); // User doesn't exist
        }

        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}