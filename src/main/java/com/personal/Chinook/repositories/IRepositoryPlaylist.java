package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("PlaylistRepo")
public interface IRepositoryPlaylist extends JpaRepository<Playlist, Integer> {
}
