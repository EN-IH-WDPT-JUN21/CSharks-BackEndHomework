package com.csharks.moviesbackend.service;

import com.csharks.moviesbackend.dao.Role;
import com.csharks.moviesbackend.dao.Users;
import com.csharks.moviesbackend.dto.RegisterUserDTO;
import com.csharks.moviesbackend.repository.RoleRepository;
import com.csharks.moviesbackend.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class UsersService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    // -------------------- Register / Login methods --------------------
    public Users registerUser(RegisterUserDTO registerUserDTO) {
        registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        log.info("Registering user: {}", registerUserDTO);
        var newUser = usersRepository.save(new Users(registerUserDTO));
        addRoleToUser(newUser.getUsername(), "USER");
        return newUser;
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role: {} to user: {}", roleName, username);
        var user = getUserByUsername(username);
        var storedRole = roleRepository.findByName(roleName);
        user.getRoles().add(
                storedRole
                        .orElseGet(() -> roleRepository.save(new Role(roleName)))
        );
        usersRepository.save(user);
    }


    // -------------------- User detail methods --------------------
    public Users getUserById(Long id) {
        var storedUser = usersRepository.findById(id);
        return storedUser.orElseThrow(() -> new RuntimeException("User not found."));
    }

    public Users getUserByUsername(String username) {
        var storedUser = usersRepository.findByUsername(username);
        return storedUser.orElseThrow(() -> new RuntimeException("User not found."));
    }

    public Users setUser(Long id, Optional<String> picture, Optional<String> bio, Optional<String> password, Optional<String> username) {
        Optional<Users> updateUser = usersRepository.findById(id);
        if (picture.isPresent()) {
            updateUser.get().setPictureUrl(picture.get());
            usersRepository.save(updateUser.get());
        } else if (bio.isPresent()) {
            updateUser.get().setBio(bio.get());
            usersRepository.save(updateUser.get());
        } else if (password.isPresent()) {
            updateUser.get().setPassword(password.get());
            usersRepository.save(updateUser.get());
        } else if (username.isPresent()) {
            updateUser.get().setUsername(username.get());
            usersRepository.save(updateUser.get());
        }
        return updateUser.get();
    }

}
