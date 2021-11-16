package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.service.PlaylistsService;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

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

//  http://localhost:8000/movie-app/playlists/all
    @GetMapping("/all")
    public List<Playlists> getAllPlaylists(){
        return playlistsRepository.findAll();
    }
//  http://localhost:8000/movie-app/playlists/2
    @GetMapping("/{id}")
    public Optional<Playlists> getPlaylistById(@PathVariable Long id){
        return playlistsRepository.findByPlaylistId(id);
    }
//  http://localhost:8000/movie-app/playlists/user/1
    @GetMapping("/user/{userId}")
    public Optional<List<Playlists>> getPlaylistByUserId(@PathVariable Long userId){
        return playlistsRepository.findByUserId(userId);
    }
//  http://localhost:8000/movie-app/playlists/1/delete
    @DeleteMapping("/{playlistId}/delete")
    public void deletePlaylist(@PathVariable Long playlistId){
        playlistsRepository.deleteByPlaylistId(playlistId);
    }
//  http://localhost:8000/movie-app/playlists/2/add/999
    @PutMapping("/{playlistId}/add/{titleId}")
    public Playlists addMovie(@PathVariable Long playlistId, @PathVariable Long titleId){
        return playlistsService.addMovieToPlaylist(playlistId, titleId);
    }
//  http://localhost:8000/movie-app/playlists/2/remove/103
    @PutMapping("/{playlistId}/remove/{titleId}")
    public Playlists removeMovie(@PathVariable Long playlistId, @PathVariable Long titleId){
        return playlistsService.removeMovieFromPlaylist(playlistId, titleId);
    }

}
