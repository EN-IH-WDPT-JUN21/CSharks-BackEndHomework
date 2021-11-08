package com.csharks.moviesbackend.repository;

import com.csharks.moviesbackend.dao.Playlists;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistsRepository extends JpaRepository<Playlists, Long> {

    Optional<List<Playlists>> findByUserId(Long userId);

    Optional<Playlists> findByPlaylistId(Long playlistId);

    void deleteByPlaylistId(Long playlistsId);



}
