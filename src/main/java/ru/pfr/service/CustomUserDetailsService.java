package ru.pfr.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        /*UserDetails userDetails = null;
        User user = userService.getByLogin(s);
        userDetails = new CustomUserDetails(user,
                true, true, true, true,
                getAuthorities(user.getRole()));

        return userDetails;*/
        return null;
    }
}
