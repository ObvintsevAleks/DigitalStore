package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRepositoryGenre extends JpaRepository<Genre, Integer> {
}
