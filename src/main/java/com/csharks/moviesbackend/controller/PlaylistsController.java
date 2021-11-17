package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import com.csharks.moviesbackend.repository.UsersRepository;
import com.csharks.moviesbackend.service.PlaylistsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@CrossOrigin(value = "*")
@Transactional
@RestController
@RequestMapping("/movie-app/playlists")

public class PlaylistsController {

    @Autowired
    PlaylistsRepository playlistsRepository;
    @Autowired
    PlaylistsService playlistsService;
    @Autowired
    MoviesRepository moviesRepository;
    @Autowired
    UsersRepository usersRepository;

    // -------------------- Playlist Methods [USER] --------------------
    //  http://localhost:8000/movie-app/playlists/user/authenticated
    @GetMapping("/user/authenticated")
    public Optional<List<Playlists>> getPlaylistByUserId(Authentication auth) {
        var userId = usersRepository.findByUsername(auth.getName())
                .map(Users::getId)
                .orElse(0L);
        return playlistsRepository.findByUserId(userId);
    }

    // TODO - JA: Delete unused methods. Add required methods.
    // --------------------  --------------------
    //  http://localhost:8000/movie-app/playlists/all
    @GetMapping("/all")
    public List<Playlists> getAllPlaylists() {
        return playlistsRepository.findAll();
    }

    //  http://localhost:8000/movie-app/playlists/2
    @GetMapping("/{id}")
    public Optional<Playlists> getPlaylistById(@PathVariable Long id) {
        return playlistsRepository.findByPlaylistId(id);
    }

    //  http://localhost:8000/movie-app/playlists/user/1
    @GetMapping("/user/{userId}")
    public Optional<List<Playlists>> getPlaylistByUserId(@PathVariable Long userId) {
        return playlistsRepository.findByUserId(userId);
    }

    //  http://localhost:8000/movie-app/playlists/1/delete
    @DeleteMapping("/{playlistId}/delete")
    public void deletePlaylist(@PathVariable Long playlistId) {
        playlistsRepository.deleteByPlaylistId(playlistId);
    }

    //  http://localhost:8000/movie-app/playlists/2/add/999
    @PutMapping("/{playlistId}/add/{titleId}")
    public Playlists addMovie(@PathVariable Long playlistId, @PathVariable Long titleId) {
        return playlistsService.addMovieToPlaylist(playlistId, titleId);
    }

    //  http://localhost:8000/movie-app/playlists/2/remove/103
    @PutMapping("/{playlistId}/remove/{titleId}")
    public Playlists removeMovie(@PathVariable Long playlistId, @PathVariable Long titleId) {
        return playlistsService.removeMovieFromPlaylist(playlistId, titleId);
    }

}
