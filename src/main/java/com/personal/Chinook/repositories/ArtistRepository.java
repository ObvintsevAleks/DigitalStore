package com.personal.Chinook.repositories;

import com.personal.Chinook.models.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Integer> {

    @Query("select e from Artist e where e.name like %:name% ")
    List<Artist> searchByName(@Param("name") String name);

    //long count();
}
