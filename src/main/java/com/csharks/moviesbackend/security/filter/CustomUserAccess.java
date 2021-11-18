package com.csharks.moviesbackend.security.filter;

import com.csharks.moviesbackend.repository.PlaylistsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;


// TODO: fix bug when using this with jwt security
@Slf4j
public class CustomUserAccess {
    @Autowired
    private PlaylistsRepository playlistsRepository;


    // Checks if playlist id belongs to the logged user. Or if is Admin.
    public boolean checkUsernameFromPlaylistId(Authentication authentication, Long playlistId) {
        log.info("Checking if user {} has access to playlist {}", authentication.getName(), playlistId);
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

    // Checks if logged user is admin
    private boolean checkAdminRole(Authentication authentication) {
        log.info("Checking if user {} has admin role", authentication.getName());
        return authentication.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
    }

}
