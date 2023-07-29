package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository("GenreRepo")
public interface IRepositoryGenre extends JpaRepository<Genre, Integer> {

    @Query("select e from Genre e where e.name like %:name%")
    List<Genre> searchByName(@Param("name") String name);
}
