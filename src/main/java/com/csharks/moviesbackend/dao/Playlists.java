package com.csharks.moviesbackend.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Playlists {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playlistId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Users user;

    private String name;
    private boolean visible;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "playlists_titles",
            joinColumns = @JoinColumn(name = "playlist_id"),
            inverseJoinColumns = @JoinColumn(name = "title_id"))
    private List<Movies> movies;


    // -------------------- Constructors --------------------
    public Playlists(Users user, String name, boolean visible, Movies... movies) {
        this.user = user;
        this.name = name;
        this.visible = visible;
        this.movies = Stream.of(movies).collect(Collectors.toList());
    }

    public Playlists(Users user, String name, boolean visible, Movies movies ) {
        this.user = user;
        this.name = name;
        this.visible = visible;
        this.movies = new ArrayList<>();
        this.movies.add(movies);
    }

    public int getIndexMovie(String titleId){
        int result = -1;
        for (int i = 0; i < this.getMovies().size(); i++){
            if(this.getMovies().get(i).getTitleId().equals(titleId)){
                result = i;  //Long.valueOf(
            }
        } return result;
    }
}
