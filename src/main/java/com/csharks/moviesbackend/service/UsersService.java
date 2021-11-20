package com.csharks.moviesbackend.service;

import com.csharks.moviesbackend.dao.Roles;
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
@Slf4j
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
// this and the private final objects create a constructor that does the same as the @Autowired (but only sometimes)
public class UsersService {
    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    // -------------------- Register / Login methods --------------------
    public Users registerUser(RegisterUserDTO registerUserDTO) {
        registerUserDTO.setPassword(passwordEncoder.encode(registerUserDTO.getPassword()));
        log.info("Registering user: {}", registerUserDTO);
        Users newUser = usersRepository.save(new Users(registerUserDTO));
        addRoleToUser(newUser.getUsername(), "USER");
        return newUser;
    }

    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role: {} to user: {}", roleName, username);
        Users user = getUserByUsername(username);
        Optional<Roles> storedRole = roleRepository.findByName(roleName);
        user.getRoles().add(
                storedRole.orElseGet(() -> roleRepository.save(new Roles(roleName)))
        );
        usersRepository.save(user);
    }


    // -------------------- User detail methods --------------------
    public Users getUserById(Long id) {
        log.info("Getting user: {}", id);
        Optional<Users> storedUser = usersRepository.findById(id);
        return storedUser.orElseThrow(() -> new RuntimeException("User not found."));
    }

    public Users getUserByUsername(String username) {
        log.info("Getting user: {}", username);
        Optional<Users> storedUser = usersRepository.findByUsername(username);
        return storedUser.orElseThrow(() -> new RuntimeException("User not found."));
    }

    // -------------------- User update methods --------------------
    public Users setUserByUsername(String username, Optional<String> picture, Optional<String> bio, Optional<String> password) {
        log.info("Updating user: {}", username);
        Optional<Users> updateUser = usersRepository.findByUsername(username);
        return updateUser   // if the user exists
                .map(users -> updateUserDetails(picture, bio, password, users)) // update the user
                .orElseThrow(() -> new RuntimeException("User not found."));    // else throw an exception
    }

    public Users setUserById(Long id, Optional<String> picture, Optional<String> bio, Optional<String> password) {
        log.info("Updating user: {}", id);
        Optional<Users> updateUser = usersRepository.findById(id);
        return updateUser   // if the user exists
                .map(users -> updateUserDetails(picture, bio, password, users)) // update the user
                .orElseThrow(() -> new RuntimeException("User not found."));    // else throw an exception
    }

    private Users updateUserDetails(Optional<String> picture, Optional<String> bio, Optional<String> password, Users updateUser) {
        if (picture.isPresent()) {
            updateUser.setPictureUrl(picture.get());
            usersRepository.save(updateUser);
        }
        if (bio.isPresent()) {
            updateUser.setBio(bio.get());
            usersRepository.save(updateUser);
        }
        if (password.isPresent()) {
            String newPassword = passwordEncoder.encode(password.get());
            updateUser.setPassword(newPassword);
            usersRepository.save(updateUser);
        }
        return updateUser;
    }

}
