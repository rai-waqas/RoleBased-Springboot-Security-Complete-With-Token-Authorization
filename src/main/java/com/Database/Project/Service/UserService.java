package com.Database.Project.Service;

import com.Database.Project.Model.User;
import com.Database.Project.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public boolean existsUser(Long userId){
        return userRepository.existsById(userId);
    }
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}