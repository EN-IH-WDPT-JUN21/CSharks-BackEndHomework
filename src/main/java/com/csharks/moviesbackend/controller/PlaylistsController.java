package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import com.csharks.moviesbackend.repository.UsersRepository;
import com.csharks.moviesbackend.service.PlaylistsService;
import com.csharks.moviesbackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.NOT_FOUND;

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
    @Autowired
    UsersService usersService;


    // -------------------- Playlist Methods [USER] --------------------

    //  GET: http://localhost:8000/movie-app/playlists/user/authenticated
    @GetMapping("/user/authenticated")
    public Optional<List<Playlists>> getPlaylistByUserId(Authentication auth) {
        long userId = usersRepository.findByUsername(auth.getName())
                .map(Users::getId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "User not found"));
        return playlistsRepository.findByUserId(userId);
    }

    //  GET: http://localhost:8000/movie-app/playlists/2
    @GetMapping("/{playlistId}")
    public Optional<Playlists> getPlaylistById(Authentication auth, @PathVariable Long playlistId) {
        Playlists storedPlaylist = playlistsRepository.findByPlaylistId(playlistId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Playlist not found"));

        if (storedPlaylist.isVisible() || playlistsService.isValidUserByPlaylistId(auth, playlistId)) {
            return playlistsRepository.findByPlaylistId(playlistId);
        } else
            throw new ResponseStatusException(FORBIDDEN, "You are not authorized to view this playlist");
    }

    //    //  GET: http://localhost:8000/movie-app/playlists/visible
//    @GetMapping("/visible")
//    public List<Playlists> getAllVisiblePlaylists() {
//        return playlistsService.getVisiblePlaylists();
//    }
    //  GET: http://localhost:8000/movie-app/playlists/visible
    @GetMapping("/visible")
    public List<Playlists> searchVisiblePlaylists(@RequestParam(value = "search", required = false) String search) {
        if (search != null && !search.isEmpty()) return playlistsService.searchVisiblePlaylists(search);
        return playlistsService.getVisiblePlaylists();
    }


    //  GET: http://localhost:8000/movie-app/playlists/2/movies
    @GetMapping("/{playlistId}/movies")
    public List<String> getAllMoviesIdByPlaylistId(Authentication auth, @PathVariable Long playlistId) {
        Playlists storedPlaylist = playlistsRepository.findByPlaylistId(playlistId)
                .orElseThrow(() -> new ResponseStatusException(NOT_FOUND, "Playlist not found"));

        if (storedPlaylist.isVisible() || playlistsService.isValidUserByPlaylistId(auth, playlistId)) {
            return playlistsService.getMoviesFromPlaylist(storedPlaylist);
        } else
            throw new ResponseStatusException(FORBIDDEN, "You are not authorized to view this playlist");
    }

    //  DELETE: http://localhost:8000/movie-app/playlists/1/delete
    @DeleteMapping("/{playlistId}/delete")
    public void deletePlaylistById(Authentication auth, @PathVariable Long playlistId) {
        if (playlistsService.isValidUserByPlaylistId(auth, playlistId)) {
            playlistsRepository.deleteByPlaylistId(playlistId);
        } else
            throw new ResponseStatusException(FORBIDDEN, "You are not authorized to manage this playlist");
    }


    // -------------------- Movie Methods [USER] --------------------

    //  PUT: http://localhost:8000/movie-app/playlists/2/add/999
    @PutMapping("/{playlistId}/add/{titleId}")
    public Playlists addMovie(Authentication auth, @PathVariable Long playlistId, @PathVariable String titleId) {
        if (playlistsService.isValidUserByPlaylistId(auth, playlistId)) {
            return playlistsService.addMovieToPlaylist(playlistId, titleId);
        } else
            throw new ResponseStatusException(FORBIDDEN, "You are not authorized to manage this playlist");
    }

    //  PUT: http://localhost:8000/movie-app/playlists/2/remove/103
    @PutMapping("/{playlistId}/remove/{titleId}")
    public Playlists removeMovie(Authentication auth, @PathVariable Long playlistId, @PathVariable String titleId) {
        if (playlistsService.isValidUserByPlaylistId(auth, playlistId)) {
            return playlistsService.removeMovieFromPlaylist(playlistId, titleId);
        } else
            throw new ResponseStatusException(FORBIDDEN, "You are not authorized to manage this playlist");
    }


    // -------------------- Special Methods [ADMIN] --------------------
    //  GET: http://localhost:8000/movie-app/playlists/all
    @GetMapping("/admin/all")
    public List<Playlists> getAllPlaylists() {
        return playlistsRepository.findAll();
    }

    //  GET: http://localhost:8000/movie-app/playlists/user/1
    @GetMapping("/admin/user/{userId}")
    public Optional<List<Playlists>> getPlaylistByUserId(@PathVariable Long userId) {
        return playlistsRepository.findByUserId(userId);
    }

}
