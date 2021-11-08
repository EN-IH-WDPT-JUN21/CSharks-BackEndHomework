package com.csharks.moviesbackend.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String emailAddress;
    private String password;
    private String pictureUrl;
    private String bio;
//    private LocalDate dateOfBirth;
//    private String gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Playlists> playlists;

    // constructor for registration
    public Users(String username, String emailAddress, String password) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public Users(String username, String emailAddress, String password, String pictureUrl, String bio) {
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.pictureUrl = pictureUrl;
        this.bio = bio;

    }
}
