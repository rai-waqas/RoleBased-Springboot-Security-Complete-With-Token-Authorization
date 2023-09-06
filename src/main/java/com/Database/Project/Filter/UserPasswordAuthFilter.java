package com.Database.Project.Filter;

import com.Database.Project.Authorization.UserPassAuthToken;
import com.Database.Project.Helper.JwtUtils;
import com.Database.Project.Service.UserDetailsSer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserPasswordAuthFilter extends OncePerRequestFilter {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserDetailsSer userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain)
            throws ServletException, IOException {
//        String userName = httpServletRequest.getHeader("username");
//        String password = httpServletRequest.getHeader("password");
        String tokenCheck = httpServletRequest.getHeader("Authorization");
        if (tokenCheck!=null) {
            try {
                String jwt = parseJwt(httpServletRequest);
                if (jwt != null && jwtUtils.validateJwtToken(jwt)) {
                    String username = jwtUtils.getUserNameFromJwtToken(jwt);

                    UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                    UserPassAuthToken authentication =
                            new UserPassAuthToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));

                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            } catch (Exception e) {
                logger.error("Cannot set user authentication: {}", e);
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
        else {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            throw new ServletException("Unauthorized Request");
        }
    }
    @Override
    protected boolean shouldNotFilter(HttpServletRequest req)
            throws ServletException {
//        System.out.println(req.getServletPath().equals("/login"));
        return req.getServletPath().equals("/api/auth/login");
    }
    public String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }

        return null;
    }
}
