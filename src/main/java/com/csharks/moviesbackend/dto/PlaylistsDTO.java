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
public class PlaylistsDTO {

    private Long playlistId;
    private Users user;
    private String name;
    private boolean visible;


    // -------------------- Constructors --------------------
    public PlaylistsDTO(Users user, String name, boolean visible) {
        this.user = user;
        this.name = name;
        this.visible = visible;
    }

}
