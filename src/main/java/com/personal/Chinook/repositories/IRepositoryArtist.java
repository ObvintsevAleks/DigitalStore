package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("ArtistRepository")
public interface IRepositoryArtist extends JpaRepository<Artist, Integer> {
}
