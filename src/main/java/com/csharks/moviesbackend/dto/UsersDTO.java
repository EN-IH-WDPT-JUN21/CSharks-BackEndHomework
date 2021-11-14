package com.csharks.moviesbackend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsersDTO {

    private String username;
    private String emailAddress;
    private String password;
    private String pictureUrl;
    private String bio;

//    @OneToMany(mappedBy = "playlistId", cascade = CascadeType.ALL)
//    private List<PlaylistsDTO> playlistId;

}
