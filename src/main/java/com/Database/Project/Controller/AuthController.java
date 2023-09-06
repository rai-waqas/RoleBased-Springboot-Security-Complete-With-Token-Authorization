package com.Database.Project.Controller;

import com.Database.Project.Authorization.UserPassAuthToken;
import com.Database.Project.Helper.JwtAuthResponse;
import com.Database.Project.Helper.JwtUtils;
import com.Database.Project.Service.UserDetailsSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsSer userDetailsSer;

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestHeader("username") String username,
            @RequestHeader("password") String password
            ) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UserPassAuthToken(username, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(e.getMessage());
        }
        SecurityContextHolder.getContext().setAuthentication(auth);

        String jwtToken = jwtUtils.generateJwtToken(auth);
        UserDetails userDetails = userDetailsSer.loadUserByUsername(auth.getName());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtAuthResponse(jwtToken,
                userDetails.getUsername(),
                roles));

    }

}
