package com.csharks.moviesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoviesDTO {

    private Long titleId;
    private String name;
    private boolean visible;
    private Set<PlaylistsDTO> playlists;

}
