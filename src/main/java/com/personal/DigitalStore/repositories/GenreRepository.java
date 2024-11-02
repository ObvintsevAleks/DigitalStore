package com.personal.DigitalStore.repositories;

import com.personal.DigitalStore.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface GenreRepository extends JpaRepository<Genre, UUID> {

    @Query("select e from Genre e where e.name like %:name%")
    List<Genre> searchByName(@Param("name") String name);
}
