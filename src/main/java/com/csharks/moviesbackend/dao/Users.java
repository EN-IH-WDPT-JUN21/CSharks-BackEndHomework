package com.csharks.moviesbackend.dao;

import com.csharks.moviesbackend.dto.RegisterUserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean isActive;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    private String username;
    private String emailAddress;
    private String password;
    private String pictureUrl;
    private String bio;
//    private LocalDate dateOfBirth;
//    private String gender;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Playlists> playlists = new ArrayList<>();

    // constructor for registration
    public Users(RegisterUserDTO registerUserDTO) {
        this.isActive = true;
        this.username = registerUserDTO.getUsername();
        this.emailAddress = registerUserDTO.getEmailAddress();
        this.password = registerUserDTO.getPassword();
        this.pictureUrl = "";
        this.bio = "";
        this.roles = new HashSet<>();
    }

    public Users(String username, String emailAddress, String password, String pictureUrl, String bio) {
        this.isActive = true;
        this.username = username;
        this.emailAddress = emailAddress;
        this.password = password;
        this.pictureUrl = pictureUrl;
        this.bio = bio;
    }
}
