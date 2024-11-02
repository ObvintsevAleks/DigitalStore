package com.personal.DigitalStore.repositories;

import com.personal.DigitalStore.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AlbumRepository extends JpaRepository<Album, UUID> {

    @Query("select e from Album e where e.title like %:title% ")
    Optional<List<Album>> searchByTitle(@Param("title") String title);

    @Query("select a from Album a where a.artist.id = :artistId")
    Optional<List<Album>> searchByArtistId(@Param("artistId") UUID artistId);
}
