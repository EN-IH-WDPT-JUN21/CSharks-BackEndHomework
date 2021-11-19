package com.csharks.moviesbackend.service;

import com.csharks.moviesbackend.dao.Movies;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.PlaylistsDTO;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistsService {

    @Autowired
    PlaylistsRepository playlistsRepository;
    @Autowired
    MoviesRepository moviesRepository;

    public Playlists createPlaylist(Users users, PlaylistsDTO playlistsDTO) {
        Playlists newPlaylist = new Playlists(
                users,
                playlistsDTO.getName(),
                playlistsDTO.isVisible()
        );
        playlistsRepository.save(newPlaylist);
        return newPlaylist;
    }

    public Playlists addMovieToPlaylist(Long playlistId, String titleId) {
        Playlists playlist = playlistsRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        Movies newTitle = new Movies(titleId);
        playlist.getMovies().add(newTitle);
        return playlistsRepository.save(playlist);
    }

    public Playlists removeMovieFromPlaylist(Long playlistId, String titleId) {
        Playlists playlist = playlistsRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        boolean playlistContainsMovie = playlist.getMovies().stream() // check if exists to prevent errors
                .anyMatch(movie -> movie.getTitleId().equals(titleId));
        if (!playlistContainsMovie) return playlist;

        int id = playlist.getIndexMovie(titleId);
        long movieId = playlist.getMovies().get(id).getMovieId();
        Optional<Movies> movieToRemove = moviesRepository.findById(movieId);
        playlist.getMovies().remove(id);
        moviesRepository.delete(movieToRemove.get());
        return playlistsRepository.save(playlist);
    }

    public List<String> getMoviesFromPlaylist(Playlists playlist) {
        List<Movies> movieList = playlist.getMovies();
        return movieList.stream()
                .map(Movies::getTitleId)
                .collect(Collectors.toList());
    }

    public List<Playlists> getVisiblePlaylists() {
        return playlistsRepository.findByVisible(true);
    }

    public List<Playlists> searchVisiblePlaylists(String searchQuery) {
        return playlistsRepository.findByVisibleAndNameContaining(true, searchQuery);
    }

    public boolean isValidUserByPlaylistId(Authentication auth, Long id) {
        var storedPlaylist = playlistsRepository.findById(id);
        var username = storedPlaylist
                .map(playlist -> playlist.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        return auth.getName().equals(username) || isAdmin;
    }

}
