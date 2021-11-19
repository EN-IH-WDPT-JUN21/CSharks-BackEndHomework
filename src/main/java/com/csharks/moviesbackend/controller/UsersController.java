package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.EmailDTO;
import com.csharks.moviesbackend.dto.PlaylistsDTO;
import com.csharks.moviesbackend.dto.RegisterUserDTO;
import com.csharks.moviesbackend.dto.UsernameDTO;
import com.csharks.moviesbackend.repository.UsersRepository;
import com.csharks.moviesbackend.service.PlaylistsService;
import com.csharks.moviesbackend.service.UsersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@CrossOrigin(value = "*")
@RestController
@RequestMapping("/movie-app/users")
@Slf4j
public class UsersController {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UsersService usersService;
    @Autowired
    PlaylistsService playlistsService;
    @Autowired
    PasswordEncoder passwordEncoder;


    // -------------------- Register Methods [PUBLIC] --------------------
    /*
    REGISTRATION (POST): http://localhost:8000/movie-app/users/register
    {
        "username": "User#X",
        "emailAddress": "userx@gmail.com",
        "password": "userx"
    }
    */
    @PostMapping("/register")
    public Users registerUser(@RequestBody RegisterUserDTO registerUserDTO) {
        return usersService.registerUser(registerUserDTO);
    }

    /*
    USERNAME VALIDATION (POST): http://localhost:8000/movie-app/users/validate/username
    {
        "username": "User#X"
    }
    */
    @PostMapping("/validate/username")
    public boolean isValidUsername(@RequestBody @Valid UsernameDTO usernameDTO) {
        log.info("Validating username [ " + usernameDTO.getUsername() + " ] for registration");
        return usersRepository.findByUsername(usernameDTO.getUsername()).isPresent();
    }

    /*
    EMAIL VALIDATION (POST): http://localhost:8000/movie-app/users/validate/email
    {
        "emailAddress": "userx@gmail.com"
    }
    */
    @PostMapping("/validate/email")
    public boolean isValidEmail(@RequestBody @Valid EmailDTO emailDTO) {
        log.info("Validating email [ " + emailDTO.getEmailAddress() + " ] for registration");
        return usersRepository.findByEmailAddress(emailDTO.getEmailAddress()).isPresent();
    }


    // -------------------- User Methods [USER] --------------------

    //  GET: http://localhost:8000/movie-app/users/authenticated
    @GetMapping("/authenticated")
    public Users getAuthenticatedUser(Authentication auth) {
        var username = auth.getName();
        log.info("Getting authenticated user with the username: {}", username);
        return usersService.getUserByUsername(username);
    }

    /*
    PATCH:
        http://localhost:8000/movie-app/users/authenticated/set?picture=foto1
        http://localhost:8000/movie-app/users/authenticated/set?bio=new bio description...
        http://localhost:8000/movie-app/users/authenticated/set?password=new_pass
    */
    @PatchMapping("/authenticated/set")
    public Users setAuthenticatedUser(Authentication auth,
                                      @RequestParam Optional<String> picture, @RequestParam Optional<String> bio,
                                      @RequestParam Optional<String> password) {
        var username = auth.getName();
        log.info("Changing account details of user: {}", username);
        return usersService.setUserByUsername(username, picture, bio, password);
    }

    /*
    POST: http://localhost:8000/movie-app/users/authenticated/createPlaylist
    {
        "name": "Playlist#1",
        "visible": true
    }
    */
    @PostMapping("/authenticated/createPlaylist")
    public Playlists createPlaylistForAuthenticatedUser(Authentication auth, @RequestBody PlaylistsDTO playlistsDTO) {
        log.info("New playlist being created");
        var username = auth.getName();
        Users user = usersService.getUserByUsername(username);
        return playlistsService.createPlaylist(user, playlistsDTO);
    }


// -------------------- Special Methods [ADMIN] --------------------

    //  GET: http://localhost:8000/movie-app/users/all
    @GetMapping("/admin/all")
    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

    //  GET: http://localhost:8000/movie-app/users/2
    @GetMapping("/admin/{id}")
    public Optional<Users> getUserById(@PathVariable Long id) {
        return usersRepository.findById(id);
    }


    /*
    http://localhost:8000/movie-app/users/4/set?picture=foto1
    http://localhost:8000/movie-app/users/4/set?bio=M
    http://localhost:8000/movie-app/users/4/set?username=new_name
    http://localhost:8000/movie-app/users/4/set?password=new_pass
     */
    @PatchMapping("/admin/{userId}/set")
    public Users setUser(@PathVariable Long userId,
                         @RequestParam Optional<String> picture, @RequestParam Optional<String> bio,
                         @RequestParam Optional<String> password) {
        log.info("Changing account details of user id: {}", userId);
        return usersService.setUserById(userId, picture, bio, password);
    }

    /*  http://localhost:8000/movie-app/users/1/createPlaylist
      {
        "name": "Playlist#1",
        "visible": true
      }
    */
    @PostMapping("/admin/{id}/createPlaylist")
    public Playlists createPlaylist(@PathVariable Long id, @RequestBody PlaylistsDTO playlistsDTO) {
        log.info("New playlist being created");
        Users user = usersService.getUserById(id);
        return playlistsService.createPlaylist(user, playlistsDTO);
    }

}
