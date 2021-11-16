package com.csharks.moviesbackend.controller;

import com.csharks.moviesbackend.PlaylistsService;
import com.csharks.moviesbackend.repository.MoviesRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PlaylistsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    MoviesRepository moviesRepository;
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
    void getAllPlaylists() throws Exception {
        MvcResult result = mockMvc.perform(get("/movie-app/playlists/all")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Saturday evenings"));
        assertTrue(result.getResponse().getContentAsString().contains("When it's raining"));
        assertFalse(result.getResponse().getContentAsString().contains("User#X"));
    }

    @Test
    void getPlaylistById() throws Exception {
        MvcResult result = mockMvc.perform(get("/movie-app/playlists/2")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Saturday evenings"));
        assertFalse(result.getResponse().getContentAsString().contains("User#X"));
    }

    @Test
    void getPlaylistByUserId() throws Exception {
        MvcResult result = mockMvc.perform(get("/movie-app/playlists/user/2")).andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Saturday evenings"));
        assertTrue(result.getResponse().getContentAsString().contains("When it's raining"));
        assertFalse(result.getResponse().getContentAsString().contains("Funny"));
    }

    @Test
    void deletePlaylist() {
    }

    @Test
    void addMovie() {

    }

    @Test
    void removeMovie() {
    }
}