package com.personal.Chinook.services.custom_functions;

import java.util.List;

public interface INameQuery<EntityType> {
    List<EntityType> getByName(String name);
}
