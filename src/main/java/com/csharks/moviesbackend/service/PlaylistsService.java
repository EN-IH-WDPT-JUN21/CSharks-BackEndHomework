package com.csharks.moviesbackend.service;

import com.csharks.moviesbackend.dao.Movies;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.PlaylistWithMovieDTO;
import com.csharks.moviesbackend.dto.PlaylistsDTO;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
// this and the private final objects create a constructor that does the same as the @Autowired (but only sometimes)
public class PlaylistsService {
    private final PlaylistsRepository playlistsRepository;
    private final MoviesRepository moviesRepository;

    public Playlists createPlaylist(Users users, PlaylistsDTO playlistsDTO) {
        log.info("Creating playlist for user: {}", users.getUsername());
        Playlists newPlaylist = new Playlists(
                users,
                playlistsDTO.getName(),
                playlistsDTO.isVisible()
        );
        log.info("Playlist details: {}", newPlaylist);
        return playlistsRepository.save(newPlaylist);
    }

    public Playlists addMovieToPlaylist(Long playlistId, String titleId) {
        log.info("Adding movie with title: {} to playlist: {}", titleId, playlistId);
        Playlists playlist = playlistsRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        Movies newTitle = new Movies(titleId);
        playlist.getMovies().add(newTitle);
        return playlistsRepository.save(playlist);
    }

    public Playlists removeMovieFromPlaylist(Long playlistId, String titleId) {
        log.info("Removing movie with title: {} from playlist: {}", titleId, playlistId);
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
        log.info("Getting movies from playlist: {}", playlist.getPlaylistId());
        List<Movies> movieList = playlist.getMovies();
        return movieList.stream()
                .map(Movies::getTitleId)
                .collect(Collectors.toList());
    }

    public List<Playlists> getVisiblePlaylists() {
        log.info("Getting all visible playlists");
        return playlistsRepository.findByVisible(true);
    }

    public List<Playlists> searchVisiblePlaylists(String searchQuery) {
        log.info("Searching for visible playlists with query: {}", searchQuery);
        return playlistsRepository.findByVisibleAndNameContaining(true, searchQuery);
    }


    public boolean isValidUserByPlaylistId(Authentication auth, Long id) {
        log.info("Checking if user: {} is valid for playlist: {}", auth.getName(), id);
        Optional<Playlists> storedPlaylist = playlistsRepository.findById(id);
        String username = storedPlaylist
                .map(playlist -> playlist.getUser().getUsername())
                .orElseThrow(() -> new RuntimeException("Playlist not found"));

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));

        return auth.getName().equals(username) || isAdmin;
    }
  
  
    public Playlists createPlaylistWithMovie(Users users, PlaylistWithMovieDTO playlistWithMovieDTO){
        Movies newMovie = new Movies(playlistWithMovieDTO.getMovieId());
        Playlists newPlaylist = new Playlists(users,
                playlistWithMovieDTO.getName(),
                playlistWithMovieDTO.isVisible(),
                newMovie
                );
        playlistsRepository.save(newPlaylist);
        return newPlaylist;
    }

}
