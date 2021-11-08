package com.csharks.moviesbackend;

import com.csharks.moviesbackend.dao.Movies;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.PlaylistsDTO;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlaylistsService {

    @Autowired
    PlaylistsRepository playlistsRepository;
    @Autowired
    MoviesRepository moviesRepository;

    public Playlists createPlaylist(Users users, PlaylistsDTO playlistsDTO) {
        Playlists newPlaylist = new Playlists(users,
                                              playlistsDTO.getName(),
                                              playlistsDTO.isVisible());
        playlistsRepository.save(newPlaylist);
        return newPlaylist;
    }


    public Playlists addMovieToPlaylist(Long playlistId, Long titleId) {
        Optional<Playlists> playlist = playlistsRepository.findById(playlistId);
        Movies newTitle = new Movies(titleId);
        playlist.get().getMovies().add(newTitle);
        playlistsRepository.save(playlist.get());
        return  playlist.get();
    }

    public Playlists removeMovieFromPlaylist(Long playlistId, Long titleId) {
        Optional<Playlists> playlist = playlistsRepository.findById(playlistId);
        int id = playlist.get().getIndexMovie(titleId);
        long movieId = playlist.get().getMovies().get(id).getMovieId();
        Optional<Movies> movieToRemove = moviesRepository.findByMovieId(movieId);

        playlist.get().getMovies().remove(id);
        moviesRepository.delete(movieToRemove.get());
        playlistsRepository.save(playlist.get());

        return playlist.get();
    }


}
