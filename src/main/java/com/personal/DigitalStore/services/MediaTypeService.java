package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.MediaTypeDTO;
import com.personal.DigitalStore.dto.MediaTypeSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.MediaTypeMapper;
import com.personal.DigitalStore.models.MediaType;
import com.personal.DigitalStore.repositories.MediaTypeRepository;
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
