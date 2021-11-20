package com.csharks.moviesbackend.dto;

import com.csharks.moviesbackend.dao.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlaylistWithMovieDTO {

    private Long playlistId;
    private Users user;
    private String name;
    private boolean visible;
    private String movieId;

    public PlaylistWithMovieDTO(Users user, String name, boolean visible, String movieId) {
        this.user = user;
        this.name = name;
        this.visible = visible;
        this.movieId = movieId;
    }
}
