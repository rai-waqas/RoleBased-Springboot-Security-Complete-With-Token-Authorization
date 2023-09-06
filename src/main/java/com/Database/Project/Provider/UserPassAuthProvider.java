package com.Database.Project.Provider;

import com.Database.Project.Authorization.UserPassAuthToken;
import com.Database.Project.Service.UserDetailsSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserPassAuthProvider implements AuthenticationProvider {
    @Autowired
    UserDetailsSer userDetailsService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
        if (passwordEncoder.matches(authentication.getCredentials()+"",user.getPassword()))
        {
            return new UserPassAuthToken(user.getUsername(),user.getPassword(),user.getAuthorities());
        }
        throw new BadCredentialsException("User Authentication Failed!");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UserPassAuthToken.class.equals(authentication);
    }
}
