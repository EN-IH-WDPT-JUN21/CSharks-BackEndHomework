package com.csharks.moviesbackend;

import com.csharks.moviesbackend.dao.Movies;
import com.csharks.moviesbackend.dao.Playlists;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.csharks.moviesbackend.repository.PlaylistsRepository;
import com.csharks.moviesbackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class Data {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    PlaylistsRepository playlistsRepository;
    @Autowired
    MoviesRepository moviesRepository;

//    public void deleteRep(){
//        moviesRepository
//        moviesRepository.deleteAll();
//        playlistsRepository.deleteAll();
//        usersRepository.deleteAll();
//    }

    public void populate() {

//        moviesRepository.save(new Movies(101l));
//        moviesRepository.save(new Movies(102l));
//        moviesRepository.save(new Movies(103l));
//        moviesRepository.save(new Movies(104l));
//        moviesRepository.save(new Movies(105l));

        usersRepository.save(new Users("User#1", "user1@gmail.com", "user1", "pictureURL", "M"));
        usersRepository.save(new Users("User#2", "user2@gmail.com", "user2", "pictureURL", "F"));
        usersRepository.save(new Users("User#3", "user3@gmail.com", "user3", "pictureURL", "M"));


        playlistsRepository.save(new Playlists(usersRepository.getById(1l), "Funny", true, new Movies(101l), new Movies(105l)));
        playlistsRepository.save(new Playlists(usersRepository.getById(2l), "Saturday evenings", false, new Movies(101l), new Movies(103l)));
        playlistsRepository.save(new Playlists(usersRepository.getById(2l), "When it's raining", true, new Movies(104l), new Movies(105l)));

    }
}
