package com.personal.Chinook.services.common_query_functions;

import java.util.List;
import java.util.Optional;

public interface IDBCrud<EntityType, EntityDTO> {
    List<EntityType> getAll();

    Optional<EntityType> getById(Integer id);

    EntityType persist(EntityDTO dto);

    void update(EntityDTO dto);

    void deleteById(Integer id);
}
