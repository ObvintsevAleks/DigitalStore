package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PlaylistRepo")
public interface IRepositoryPlaylist extends JpaRepository<Playlist, Integer> {

    @Query("select e from Playlist e where e.name like %:name% ")
    List<Playlist> searchByName(@Param("name") String name);
}
