package com.personal.Chinook.services;

import com.personal.Chinook.dto.MediaTypeDTO;
import com.personal.Chinook.dto.MediaTypeSaveDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mappers.MediaTypeMapper;
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
    public MediaTypeDTO createMediaType(MediaTypeSaveDTO mediaTypeSaveDTO) {
        MediaType mediaType = mediaTypeMapper.toMediaType(mediaTypeSaveDTO);
        mediaTypeRepository.save(mediaType);
        return mediaTypeMapper.toMediaTypeDTO(mediaType);
    }

    @Transactional(readOnly = true)
    public MediaTypeDTO getMediaTypeById(UUID id) throws NotFoundInDBException {
        MediaType mediaType = mediaTypeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден формат медиа файла по id = "+ id));
        return mediaTypeMapper.toMediaTypeDTO(mediaType);
    }

    @Transactional(readOnly = true)
    public List<MediaTypeDTO> getAll() {
        List<MediaType> mediaTypes = mediaTypeRepository.findAll();
        return mediaTypeMapper.toMediaTypeDTOs(mediaTypes);
    }

    @Transactional
    public MediaTypeDTO updateMediaType(MediaTypeDTO mediaTypeDTO) throws NotFoundInDBException {
        MediaType mediaTypeEntity = mediaTypeRepository.findById(mediaTypeDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найден формат медиа файла по id = "+ mediaTypeDTO.getId()));
        if (mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity).equals(mediaTypeDTO)) {
            return mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity);
        }
        mediaTypeMapper.updateMediaType(mediaTypeEntity, mediaTypeDTO);
        mediaTypeRepository.save(mediaTypeEntity);
        return mediaTypeMapper.toMediaTypeDTO(mediaTypeEntity);
    }

    @Transactional
    public MediaTypeDTO deleteMediaTypeById(UUID id) throws NotFoundInDBException {
        MediaType mediaType = mediaTypeRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден формат медиа файла по id = "+ id));
        mediaTypeRepository.deleteById(id);
        return mediaTypeMapper.toMediaTypeDTO(mediaType);
    }

}
