package com.personal.Chinook.repositories;

import com.personal.Chinook.models.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, Integer> {

    @Query("select e from MediaType e where e.name like %:name% ")
    List<MediaType> searchByName(@Param("name") String name);
}
