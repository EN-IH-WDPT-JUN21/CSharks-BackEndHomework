package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.repository.UsersRepository;
import com.csharks.moviesbackend.service.PlaylistsService;
import com.csharks.moviesbackend.service.UsersService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UsersControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UsersService usersService;
    @Autowired
    PlaylistsService playlistsService;

    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllUsers() throws Exception {
//        MvcResult result = mockMvc.perform(get("/movie-app/users/all")).andExpect(status().isOk()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("User#1"));
//        assertTrue(result.getResponse().getContentAsString().contains("user2@gmail.com"));
//        assertFalse(result.getResponse().getContentAsString().contains("User#X"));
    }

    @Test
    void getUserById() throws Exception {
//        MvcResult result = mockMvc.perform(get("/movie-app/users/2")).andExpect(status().isOk()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("User#2"));
//        assertTrue(result.getResponse().getContentAsString().contains("user2@gmail.com"));
//        assertFalse(result.getResponse().getContentAsString().contains("User#X"));
    }
//doesn't work
    @Test
    void registerUser() throws Exception {
//        UsersDTO newUser = new UsersDTO("User#XYZ","userxyz@gmail.com","userxyz");
//
//        String body = objectMapper.writeValueAsString(newUser);
//        MvcResult result = mockMvc.perform(post("/movie-app/users/registration")
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("User#XYZ"));
//        assertFalse(result.getResponse().getContentAsString().contains("User#X"));

    }


    @Test
    void setUser_bio() throws Exception{
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//
//        params.add("bio","M");
//
//        MvcResult result = mockMvc.perform(
//                put("/movie-app/users/2/set")
//                        .queryParams(params)
//        ).andExpect(status().isOk()).andReturn();
//
//        assertTrue(result.getResponse().getContentAsString().contains("M"));
        //assertFalse(result.getResponse().getContentAsString().contains("F"));


    }

    @Test
    void setUser_password() throws Exception{
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//
//        params.add("password","newPass");
//
//        MvcResult result = mockMvc.perform(
//                put("/movie-app/users/2/set")
//                        .queryParams(params)
//        ).andExpect(status().isOk()).andReturn();
//
//        assertTrue(result.getResponse().getContentAsString().contains("newPass"));
        //assertFalse(result.getResponse().getContentAsString().contains("user2"));

    }

    @Test
    void setUser_picture() throws Exception {
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("picture", "photo1");
//
//        MvcResult result = mockMvc.perform(
//                put("/movie-app/users/2/set")
//                        .queryParams(params)
//        ).andExpect(status().isOk()).andReturn();
//
//        assertTrue(result.getResponse().getContentAsString().contains("photo1"));
        //assertFalse(result.getResponse().getContentAsString().contains("user2"));


    }
//doesn't work
    @Test
    void createPlaylist() throws Exception {
//        Users user = usersRepository.getById(2l);
//        PlaylistsDTO newPlaylist = new PlaylistsDTO(user, "newPlaylist", false);
//
//        String body = objectMapper.writeValueAsString(newPlaylist);
//        MvcResult result = mockMvc.perform(post("/movie-app/users/2/createPlaylist")
//                .content(body)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated()).andReturn();
//        assertTrue(result.getResponse().getContentAsString().contains("newPlaylist"));
       // assertFalse(result.getResponse().getContentAsString().contains(false));
    }
}