package com.personal.DigitalStore.repositories;

import com.personal.DigitalStore.models.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface MediaTypeRepository extends JpaRepository<MediaType, UUID> {

    @Query("select e from MediaType e where e.name like %:name% ")
    List<MediaType> searchByName(@Param("name") String name);
}
