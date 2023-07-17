package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Track;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryTrack extends JpaRepository<Track, Integer> {
}
