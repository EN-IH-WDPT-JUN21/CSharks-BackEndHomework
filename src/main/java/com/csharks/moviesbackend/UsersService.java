package com.csharks.moviesbackend;

import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.UsersDTO;
import com.csharks.moviesbackend.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersService {

    @Autowired
    UsersRepository usersRepository;


    public Users registerUser(UsersDTO usersDTO){
        Users regNewUser =usersRepository.save(new Users(usersDTO.getUsername(), usersDTO.getEmailAddress(), usersDTO.getPassword()));
        return regNewUser;
    }

    public Users setUser(Long id, Optional<String> picture, Optional<String> bio, Optional<String> password, Optional<String> username){
        Optional<Users> updateUser = usersRepository.findById(id);
        if(picture.isPresent()){
            updateUser.get().setPictureUrl(picture.get());
            usersRepository.save(updateUser.get());
        } else  if(bio.isPresent()){
            updateUser.get().setBio(bio.get());
            usersRepository.save(updateUser.get());
        } else  if(password.isPresent()){
            updateUser.get().setPassword(password.get());
            usersRepository.save(updateUser.get());
        } else  if(username.isPresent()){
            updateUser.get().setUsername(username.get());
            usersRepository.save(updateUser.get());
        }
        return updateUser.get();
    }
}
