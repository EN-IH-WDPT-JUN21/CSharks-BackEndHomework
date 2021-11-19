package com.csharks.moviesbackend.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long movieId;

    @Column(name = "title_id")
    private String titleId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy = "movies")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Playlists> playlists;


    // -------------------- Constructors --------------------
    public Movies(String titleId) {
        this.titleId = titleId;
    }

}
