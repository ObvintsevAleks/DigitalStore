package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Album;
import com.personal.Chinook.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

    @Query("select e from Album e where e.title like %:title% ")
    List<Album> searchByTitle(@Param("title") String title);

    @Query("select e from Album e where e.artist = :artistId ")
    List<Album> searchByArtistId(@Param("ArtistId") UUID artistId);
}
