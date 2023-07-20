package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("AlbumRepository")
public interface IRepositoryAlbum extends JpaRepository<Album, Integer> {
}
