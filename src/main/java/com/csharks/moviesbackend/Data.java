package com.csharks.moviesbackend;

import com.csharks.moviesbackend.dao.Movies;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import com.csharks.moviesbackend.repository.UsersRepository;
import com.csharks.moviesbackend.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;


@Component
public class Data {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private PlaylistsRepository playlistsRepository;

    @Autowired
    private MoviesRepository moviesRepository;

    @Autowired
    private UsersService usersService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public void populate() {

//        moviesRepository.save(new Movies(101l));
//        moviesRepository.save(new Movies(102l));
//        moviesRepository.save(new Movies(103l));
//        moviesRepository.save(new Movies(104l));
//        moviesRepository.save(new Movies(105l));

        usersRepository.save(new Users("User#1", "user1@gmail.com", bCryptPasswordEncoder.encode("user1"), "pictureURL", "M"));
        usersService.addRoleToUser("User#1", "USER");
        usersRepository.save(new Users("User#2", "user2@gmail.com", bCryptPasswordEncoder.encode("user2"), "pictureURL", "F"));
        usersService.addRoleToUser("User#2", "USER");
        usersRepository.save(new Users("User#3", "user3@gmail.com", bCryptPasswordEncoder.encode("user3"), "pictureURL", "M"));
        usersService.addRoleToUser("User#3", "USER");

        usersRepository.save(new Users("Admin", "admin@gmail.com", bCryptPasswordEncoder.encode("admin"), "", ""));
        usersService.addRoleToUser("Admin", "ADMIN");

        playlistsRepository.save(new Playlists(usersRepository.getById(1L),
                "Funny", true, new Movies("tt1375666"), new Movies("tt0411008")));
        playlistsRepository.save(new Playlists(usersRepository.getById(2L),
                "Saturday evenings", true, new Movies("tt1375666"), new Movies("tt0110413"), new Movies("tt1375666")));
        playlistsRepository.save(new Playlists(usersRepository.getById(2L),
                "To watch", false, new Movies("tt1375666"), new Movies("tt1375666")));
        playlistsRepository.save(new Playlists(usersRepository.getById(3L),
                "When it's raining", true, new Movies("tt1375666")));
        playlistsRepository.save(new Playlists(usersRepository.getById(3L),
                "Favorite", false, new Movies("tt1375666")));

    }
}
