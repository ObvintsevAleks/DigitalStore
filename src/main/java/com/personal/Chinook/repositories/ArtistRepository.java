package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, UUID> {

    @Query("select e from Artist e where e.name like %:name% ")
    List<Artist> searchByName(@Param("name") String name);

    //long count();
}
