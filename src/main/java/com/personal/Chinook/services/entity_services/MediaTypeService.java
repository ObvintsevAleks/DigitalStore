package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.MediaType;
import com.personal.Chinook.repositories.IRepositoryMediaType;
import com.personal.Chinook.services.common_query_functions.IDBCrud;
import com.personal.Chinook.services.common_query_functions.INameQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service("MediaTypeService")
public class MediaTypeService implements IDBCrud<MediaType, MediaTypeDTO>, INameQuery<MediaType> {

    private final IRepositoryMediaType repoMediaType;

    @Autowired
    public MediaTypeService(@Qualifier("MediaTypeRepo") IRepositoryMediaType mediaTypeRepo) {
        this.repoMediaType = mediaTypeRepo;
    }

    @Override
    public List<MediaType> getAll() {

        return repoMediaType.findAll();
    }

    @Override
    public Optional<MediaType> getById(Integer mediaTypeId) {
        if (mediaTypeId == null)
            throw new InvalidFieldException("ERROR, found empty fields");

        return repoMediaType.findById(mediaTypeId);
    }

    @Override
    public MediaType persist(MediaTypeDTO mediaTypeDTO) {
        if (
                mediaTypeDTO.getId() == null ||
                mediaTypeDTO.getName() == null ||
                mediaTypeDTO.getName().isEmpty() ||
                mediaTypeDTO.getName().isBlank()
        )
            throw new InvalidFieldException("ERROR, found empty fields");

        if (mediaTypeDTO.getId() < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        if (!(Pattern.matches("[a-zA-Z -]+", mediaTypeDTO.getName())) )
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        if (mediaTypeDTO.getName().length() > 120)
            throw new InvalidFieldException("ERROR, name exceeds character limit (120)");

        return repoMediaType.save(
                 MediaType.builder()
                         .id(mediaTypeDTO.getId())
                         .name(mediaTypeDTO.getName().trim())
                         .build()
        );
    }

    @Override
    public void update(MediaTypeDTO mediaTypeDTO) {
        if ( !repoMediaType.existsById(mediaTypeDTO.getId()) )
            throw new NotFoundInDBException("ERROR, media type does not exist in database");

        persist(mediaTypeDTO);
    }

    @Override
    public void deleteById(Integer id) {
        if ( !(repoMediaType.existsById(id)) )
            throw new NotFoundInDBException("ERROR, media type does not exist in database");

        repoMediaType.deleteById(id);
    }

    @Override
    public List<MediaType> getByName(String name) {
        if (name == null || name.isEmpty() || name.isBlank())
            throw new InvalidFieldException("ERROR, found empty fields");

        if ( !(Pattern.matches("[a-zA-Z -]+", name)) )
            throw new InvalidFieldException("ERROR, name cannot contain special characters");

        return repoMediaType.searchByName(name);
    }
}
