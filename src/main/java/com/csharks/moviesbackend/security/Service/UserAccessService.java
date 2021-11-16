package com.csharks.moviesbackend.security.Service;

import com.csharks.moviesbackend.repository.PlaylistsRepository;
import com.csharks.moviesbackend.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

@Slf4j
public class UserAccessService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlaylistsRepository playlistsRepository;


    // Checks if user id corresponds to the user that is logged in. Or if is Admin.
    public boolean checkUsernameFromUserId(Authentication authentication, Long userId) {
        if (checkAdminRole(authentication)) return true;
        var loggedInUsername = authentication.getName();
        var storedUser = usersRepository.findById(userId);
        return storedUser
                .map(user -> {
                    log.info("Checking if user {} is the same as logged in user {}", user.getUsername(), loggedInUsername);
                    return user.getUsername().equals(loggedInUsername);
                })
                .orElseGet(() -> {
                    log.info("User with id {} not found", userId);
                    return false;
                });
    }

    // Checks if playlist id belongs to the user that is logged in. Or if is Admin.
    public boolean checkUsernameFromPlaylistId(Authentication authentication, Long playlistId) {
        if (checkAdminRole(authentication)) return true;
        var loggedInUsername = authentication.getName();
        var storedPlaylist = playlistsRepository.findByPlaylistId(playlistId);
        return storedPlaylist
                .map(playlist -> {
                    log.info("Checking if playlist {} belongs to user {}", playlist.getPlaylistId(), loggedInUsername);
                    return playlist.getUser().getUsername().equals(loggedInUsername);
                })
                .orElseGet(() -> {
                    log.info("Playlist with id {} not found", playlistId);
                    return false;
                });
    }

    // Checks if user is admin
    private boolean checkAdminRole(Authentication authentication) {
        log.info("Checking if user {} has admin role", authentication.getName());
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

}
