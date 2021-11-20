package com.csharks.moviesbackend.security.Service;

import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.repository.UsersRepository;
import com.csharks.moviesbackend.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> storedUser = usersRepository.findByUsername(username);
        log.info("Loading user with username: {}", username);
        System.out.println("Loading user with username: " + username);
        return storedUser.
                map(CustomUserDetails::new).
                orElseThrow(() -> new UsernameNotFoundException("User not found."));
    }
}
