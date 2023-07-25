package com.personal.Chinook.repositories;

import com.personal.Chinook.models.MediaType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("MediaTypeRepo")
public interface IRepositoryMediaType extends JpaRepository<MediaType, Integer> {
}
