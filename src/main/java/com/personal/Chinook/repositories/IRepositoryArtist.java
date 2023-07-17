package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryArtist extends JpaRepository<Artist, Integer> {
}
