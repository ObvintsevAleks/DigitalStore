package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.MediaTypeDTO;
import com.personal.DigitalStore.dto.MediaTypeSaveDTO;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.MediaTypeMapper;
import com.personal.DigitalStore.models.MediaType;
import com.personal.DigitalStore.repositories.MediaTypeRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@DisplayName("Unit тест — MediaTypeService")
@ExtendWith(MockitoExtension.class)
class MediaTypeServiceTest {

    @Mock
    private MediaTypeRepository mediaTypeRepository;

    @Mock
    private MediaTypeMapper mediaTypeMapper;

    @InjectMocks
    private MediaTypeService mediaTypeService;

    private static final UUID ID = UUID.fromString("8e262c04-a090-11e8-98d0-529269fb1459");
    private static final LocalDate DATE = LocalDate.of(2020, 1, 1);

    private MediaType mediaType() {
        return MediaType.builder().id(ID).name("MP3").createdAt(DATE).build();
    }

    private MediaTypeDTO mediaTypeDTO() {
        return new MediaTypeDTO(ID, "MP3", DATE);
    }

    private MediaTypeSaveDTO mediaTypeSaveDTO() {
        return new MediaTypeSaveDTO("MP3", DATE);
    }

    @Nested
    @DisplayName("createMediaType")
    class CreateMediaType {

        @Test
        @DisplayName("успешно создаёт и возвращает DTO медиа-типа")
        void success() {
            MediaTypeSaveDTO saveDTO = mediaTypeSaveDTO();
            MediaType mediaType = mediaType();
            MediaTypeDTO expected = mediaTypeDTO();

            when(mediaTypeMapper.toMediaType(saveDTO)).thenReturn(mediaType);
            when(mediaTypeMapper.toMediaTypeDTO(mediaType)).thenReturn(expected);

            MediaTypeDTO result = mediaTypeService.createMediaType(saveDTO);

            assertThat(result).isEqualTo(expected);
            verify(mediaTypeRepository).save(mediaType);
        }
    }

    @Nested
    @DisplayName("getMediaTypeById")
    class GetMediaTypeById {

        @Test
        @DisplayName("возвращает DTO когда медиа-тип найден")
        void found() {
            MediaType mediaType = mediaType();
            MediaTypeDTO expected = mediaTypeDTO();

            when(mediaTypeRepository.findById(ID)).thenReturn(Optional.of(mediaType));
            when(mediaTypeMapper.toMediaTypeDTO(mediaType)).thenReturn(expected);

            MediaTypeDTO result = mediaTypeService.getMediaTypeById(ID);

            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException когда медиа-тип не найден")
        void notFound() {
            when(mediaTypeRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> mediaTypeService.getMediaTypeById(ID))
                    .isInstanceOf(NotFoundInDBException.class)
                    .hasMessageContaining(ID.toString());
        }
    }

    @Nested
    @DisplayName("getAll")
    class GetAll {

        @Test
        @DisplayName("возвращает все медиа-типы")
        void returnsAllMediaTypes() {
            List<MediaType> mediaTypes = List.of(mediaType());
            MediaTypeDTO dto = mediaTypeDTO();

            when(mediaTypeRepository.findAll()).thenReturn(mediaTypes);
            when(mediaTypeMapper.toMediaTypeDTOs(mediaTypes)).thenReturn(List.of(dto));

            List<MediaTypeDTO> result = mediaTypeService.getAll();

            assertThat(result).containsExactly(dto);
        }

        @Test
        @DisplayName("возвращает пустой список если медиа-типов нет")
        void returnsEmptyList() {
            when(mediaTypeRepository.findAll()).thenReturn(List.of());
            when(mediaTypeMapper.toMediaTypeDTOs(List.of())).thenReturn(List.of());

            List<MediaTypeDTO> result = mediaTypeService.getAll();

            assertThat(result).isEmpty();
        }
    }

    @Nested
    @DisplayName("updateMediaType")
    class UpdateMediaType {

        @Test
        @DisplayName("обновляет медиа-тип и сохраняет изменения")
        void updatesWhenDataChanged() {
            MediaType mediaType = mediaType();
            MediaTypeDTO originalDTO = mediaTypeDTO();
            MediaTypeDTO updatedDTO = new MediaTypeDTO(ID, "WAV", DATE);

            when(mediaTypeRepository.findById(ID)).thenReturn(Optional.of(mediaType));
            // первый вызов — сравнение, второй — после обновления
            when(mediaTypeMapper.toMediaTypeDTO(mediaType)).thenReturn(originalDTO, updatedDTO);

            MediaTypeDTO result = mediaTypeService.updateMediaType(updatedDTO);

            verify(mediaTypeMapper).updateMediaType(mediaType, updatedDTO);
            verify(mediaTypeRepository).save(mediaType);
            assertThat(result).isEqualTo(updatedDTO);
        }

        @Test
        @DisplayName("не сохраняет если данные не изменились")
        void noSaveWhenDataUnchanged() {
            MediaType mediaType = mediaType();
            MediaTypeDTO dto = mediaTypeDTO();

            when(mediaTypeRepository.findById(ID)).thenReturn(Optional.of(mediaType));
            when(mediaTypeMapper.toMediaTypeDTO(mediaType)).thenReturn(dto);

            MediaTypeDTO result = mediaTypeService.updateMediaType(dto);

            verify(mediaTypeRepository, never()).save(any());
            assertThat(result).isEqualTo(dto);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если медиа-тип не найден")
        void notFound() {
            when(mediaTypeRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> mediaTypeService.updateMediaType(mediaTypeDTO()))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }

    @Nested
    @DisplayName("deleteMediaTypeById")
    class DeleteMediaTypeById {

        @Test
        @DisplayName("успешно удаляет медиа-тип")
        void success() {
            MediaType mediaType = mediaType();
            MediaTypeDTO expected = mediaTypeDTO();

            when(mediaTypeRepository.findById(ID)).thenReturn(Optional.of(mediaType));
            when(mediaTypeMapper.toMediaTypeDTO(mediaType)).thenReturn(expected);

            MediaTypeDTO result = mediaTypeService.deleteMediaTypeById(ID);

            verify(mediaTypeRepository).deleteById(ID);
            assertThat(result).isEqualTo(expected);
        }

        @Test
        @DisplayName("бросает NotFoundInDBException если медиа-тип не найден")
        void notFound() {
            when(mediaTypeRepository.findById(ID)).thenReturn(Optional.empty());

            assertThatThrownBy(() -> mediaTypeService.deleteMediaTypeById(ID))
                    .isInstanceOf(NotFoundInDBException.class);
        }
    }
}
