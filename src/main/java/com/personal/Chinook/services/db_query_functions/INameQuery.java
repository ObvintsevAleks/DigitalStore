package com.personal.Chinook.services.db_query_functions;

import java.util.List;

public interface INameQuery<EntityType> {
    List<EntityType> getByName(String name);
}
