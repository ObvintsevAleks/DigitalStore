package com.personal.Chinook.services.common_query_functions;

import java.util.List;

public interface IPersonalDataQueries<EntityType> {
    List<EntityType> getByFirstName(String firstName);

    List<EntityType> getByLastNme(String lastName);

    List<EntityType> getByEmail(String email);

    List<EntityType> getByFax(String fax);

    List<EntityType> getByPhone(String phone);
}
