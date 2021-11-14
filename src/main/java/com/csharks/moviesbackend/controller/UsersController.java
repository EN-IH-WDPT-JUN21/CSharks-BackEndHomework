package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.PlaylistsService;
import com.csharks.moviesbackend.UsersService;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.PlaylistsDTO;
import com.csharks.moviesbackend.dto.UsersDTO;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import com.csharks.moviesbackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(value ="*")
@RestController
@RequestMapping("/movie-app/users")

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
    public Users registerUser(@RequestBody UsersDTO usersDTO){
        return usersService.registerUser(usersDTO);
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
