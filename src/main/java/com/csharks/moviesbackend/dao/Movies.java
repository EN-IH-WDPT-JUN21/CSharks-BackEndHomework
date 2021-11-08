package com.csharks.moviesbackend.dao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Movies {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieId;

    private Long titleId;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE},
                mappedBy = "movies")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Playlists> playlists;

    public Movies(Long titleId) {
        this.titleId = titleId;
    }
}
