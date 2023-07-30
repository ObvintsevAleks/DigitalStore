package com.personal.Chinook.services.common_query_functions;

import java.util.List;

public interface ILocationQueries<EntityType> {
    List<EntityType> getByCountry(String country);

    List<EntityType> getByState(String state);

    List<EntityType> getByCity(String city);

    List<EntityType> getByAddress(String address);

    List<EntityType> getByPostalCode(String postalCode);
}
