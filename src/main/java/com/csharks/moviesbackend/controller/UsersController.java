package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.PlaylistsService;
import com.csharks.moviesbackend.dto.EmailDTO;
import com.csharks.moviesbackend.dto.UsernameDTO;
import com.csharks.moviesbackend.service.UsersService;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.PlaylistsDTO;
import com.csharks.moviesbackend.dto.RegisterUserDTO;
import com.csharks.moviesbackend.repository.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Optional;

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



//  http://localhost:8000/movie-app/users/all
    @GetMapping("/all")
    public List<Users> getAllUsers(){
        return usersRepository.findAll();
    }

//  http://localhost:8000/movie-app/users/2
    @GetMapping("/{id}")
    public Optional<Users> getUserById(@PathVariable Long id){
        return usersRepository.findById(id);
    }

    //  http://localhost:8000/movie-app/users/username/current
    @GetMapping("/username/current")
    public Users getUserByUsername(Authentication auth){
        var username= auth.getName();
        log.info("username: {}", username);
        return usersService.getUserByUsername(username);
    }

    //  http://localhost:8000/movie-app/users/validate/username/{username}
    @PostMapping("/validate/username")
    public boolean isValidUsername(@RequestBody @Valid UsernameDTO usernameDTO) {
        log.info("username: {}", usernameDTO.getUsername());
        return usersRepository.existsByUsername(usernameDTO.getUsername());
    }

    //  http://localhost:8000/movie-app/users/validate/email/{email}
    @PostMapping("/validate/email")
    public boolean isValidEmail(@RequestBody @Valid EmailDTO emailDTO) {
        log.info("email: {}", emailDTO.getEmailAddress());
        return usersRepository.existsByEmailAddress(emailDTO.getEmailAddress());
    }

/*
REGISTRATION (POST):
http://localhost:8000/movie-app/users/register
{
    "username": "User#X",
    "emailAddress": "userx@gmail.com",
    "password": "userx"
}
 */
    @PostMapping("/register")
    public Users registerUser(@RequestBody RegisterUserDTO registerUserDTO){
        return usersService.registerUser(registerUserDTO);
    }

/*
http://localhost:8000/movie-app/users/4/set?picture=foto1
http://localhost:8000/movie-app/users/4/set?bio=M
http://localhost:8000/movie-app/users/4/set?username=new_name
http://localhost:8000/movie-app/users/4/set?password=new_pass
 */
    @PutMapping("/{id}/set")
    public Users setUser(@PathVariable Long id,
                         @RequestParam Optional<String> picture, @RequestParam Optional<String> bio,
                         @RequestParam Optional<String> password, @RequestParam Optional<String> username){
        return usersService.setUser(id, picture, bio, password, username);
    }

/*  http://localhost:8000/movie-app/users/1/createPlaylist
  {
    "name": "Playlist#1",
    "visible": true
  }
*/  @PostMapping("/{id}/createPlaylist")
    public Playlists createPlaylist(@PathVariable Long id, @RequestBody PlaylistsDTO playlistsDTO){
        Optional<Users> user = usersRepository.findById(id);
        return playlistsService.createPlaylist(user.get(), playlistsDTO);
    }


}
