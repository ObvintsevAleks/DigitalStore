package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Album;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TrackRepository extends JpaRepository<Track, UUID> {

    @Query("select tr from Track tr where tr.name like %:name% ")
    List<Track> searchByName(@Param("name") String name);

    @Query("select tr from Track tr where tr.author like %:author% ")
    List<Track> searchByAuthor(@Param("author") String author);

    @Query("select tr from Track tr where tr.genre.id = :genreId")
    List<Track> searchByGenreId(@Param("genreId") UUID genreId);

//    @Query("select tr from Track tr where tr.genre.name = :genreName")
//    List<Track> searchByGenreName(@Param("genreName") String genreName);

    @Query("select tr from Track tr where tr.album.id = :albumId")
    List<Track> searchByAlbumId(@Param("albumId") UUID albumId);

//    @Query("select tr from Track tr where tr.album.title = :albumTitle")
//    List<Track> searchByAlbumTitle(@Param("albumTitle") String albumTitle);

    @Query("select tr from Track tr where tr.album.id = :mediaTypeId")
    List<Track> searchByMediaTypeId(@Param("mediaTypeId") UUID mediaTypeId);

//    @Query("select tr from Track tr where tr.mediaType.name = :mediaTypeName")
//    List<Track> searchByMediaTypeName(@Param("mediaTypeName") String mediaTypeName);

//    @Query("select tr from Track tr where tr.album.artist.name = :artistName")
//    List<Track> searchByArtistName(@Param("artistName") String artistName);

}
