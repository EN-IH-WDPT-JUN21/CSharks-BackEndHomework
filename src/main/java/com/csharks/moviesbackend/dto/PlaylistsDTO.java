package com.csharks.moviesbackend.dto;

import com.csharks.moviesbackend.dao.Users;
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
public class PlaylistsDTO {

    private Long playlistId;
    private Users user;
    private String name;
    private boolean visible;

//    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @JoinTable(name = "playlists_titles",
//            joinColumns = { @JoinColumn(name = "playlist_id") },
//            inverseJoinColumns = { @JoinColumn(name = "title_id") })
//    private Set<MoviesDTO> movies;


    public PlaylistsDTO(Users user, String name, boolean visible) {
        this.user = user;
        this.name = name;
        this.visible = visible;
    }
}
