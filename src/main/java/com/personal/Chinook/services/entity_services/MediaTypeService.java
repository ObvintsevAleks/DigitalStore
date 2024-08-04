package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.DTO.MediaTypeSaveDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.MediaTypeMapper;
import com.personal.Chinook.models.MediaType;
import com.personal.Chinook.repositories.MediaTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaTypeService {

    private final MediaTypeRepository mediaTypeRepository;
    private final MediaTypeMapper mediaTypeMapper;


    @Transactional
    public MediaTypeDTO createMediaType(MediaTypeSaveDTO MediaTypeDTO) {
        MediaType mediaType = mediaTypeMapper.toMediaType(MediaTypeDTO);
        mediaTypeRepository.save(mediaType);
        return mediaTypeMapper.toMediaTypeDTO(mediaType);
    }


    @Transactional(readOnly = true)
    public MediaTypeDTO getMediaTypeById(UUID id) throws NotFoundInDBException {
        MediaType mediaType = mediaTypeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException(""));
        return mediaTypeMapper.toMediaTypeDTO(mediaType);
    }

    @Transactional(readOnly = true)
    public List<MediaTypeDTO> getAll() {
        List<MediaType> mediaTypes = mediaTypeRepository.findAll();
        return mediaTypeMapper.toMediaTypeDTOs(mediaTypes);
    }

    @Transactional
    public MediaTypeDTO updateMediaType(MediaTypeDTO mediaTypeDTO) throws NotFoundInDBException {
        MediaType mediaTypeEntity = mediaTypeRepository.findById(mediaTypeDTO.getId()).orElseThrow(() -> new NotFoundInDBException(""));
        if (mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity).equals(mediaTypeDTO)) {
            return mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity);
        }
        mediaTypeMapper.updateMediaType(mediaTypeEntity, mediaTypeDTO);
        mediaTypeRepository.save(mediaTypeEntity);
        return mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity);
    }

    @Transactional
    public MediaTypeDTO deleteMediaTypeById(UUID id) throws NotFoundInDBException {
        MediaType mediaType = mediaTypeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("asrd"));
        mediaTypeRepository.deleteById(id);
        return mediaTypeMapper.toMediaTypeDTO(mediaType);
    }

}
