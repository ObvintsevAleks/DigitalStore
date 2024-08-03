package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.MediaTypeMapper;
import com.personal.Chinook.models.MediaType;
import com.personal.Chinook.repositories.MediaTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MediaTypeService {

    private final MediaTypeRepository mediaTypeRepository;
    private final MediaTypeMapper mediaTypeMapper;


    @Transactional
    public MediaTypeDTO createMediaType(MediaTypeDTO MediaTypeDTO) {
        MediaType MediaType = mediaTypeMapper.toMediaType(MediaTypeDTO);
        mediaTypeRepository.save(MediaType);
        return mediaTypeMapper.toMediaTypeDTO(MediaType);
    }


    @Transactional(readOnly = true)
    public MediaTypeDTO getMediaTypeById(Integer id) throws NotFoundInDBException {
        MediaType MediaType = mediaTypeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException(""));
        return mediaTypeMapper.toMediaTypeDTO(MediaType);
    }

    @Transactional(readOnly = true)
    public List<MediaTypeDTO> getAll() {
        List<MediaType> genres = mediaTypeRepository.findAll();
        return mediaTypeMapper.toMediaTypeDTOs(genres);
    }

    @Transactional
    public MediaTypeDTO updateMediaType(Integer id, MediaTypeDTO mediaTypeDTO) throws NotFoundInDBException {
        MediaType mediaTypeEntity = mediaTypeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException(""));
        if (mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity).equals(mediaTypeDTO)) {
            return mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity);
        }
        mediaTypeMapper.updateMediaType(mediaTypeEntity, mediaTypeDTO);
        mediaTypeRepository.save(mediaTypeEntity);
        return mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity);
    }

    @Transactional
    public MediaTypeDTO deleteMediaTypeById(Integer id) throws NotFoundInDBException {
        MediaType MediaType = mediaTypeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("asrd"));
        mediaTypeRepository.deleteById(id);
        return mediaTypeMapper.toMediaTypeDTO(MediaType);
    }

}
