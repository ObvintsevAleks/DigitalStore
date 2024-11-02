package com.personal.DigitalStore.controllers;

import com.personal.DigitalStore.dto.MediaTypeDTO;
import com.personal.DigitalStore.dto.MediaTypeSaveDTO;
import com.personal.DigitalStore.services.MediaTypeService;
import com.personal.DigitalStore.utils.swagger.ApiCreate;
import com.personal.DigitalStore.utils.swagger.ApiDelete;
import com.personal.DigitalStore.utils.swagger.ApiGet;
import com.personal.DigitalStore.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "media-type-controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/media-types")
public class MediaTypeController {

    private final MediaTypeService service;

    @ApiGet
    @Operation(summary = "Получить все медиа-типы")
    @GetMapping("/all")
    public ResponseEntity<?> getAllMediaTypes() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @ApiGet
    @Operation(summary = "Получить медиа-тип по идентификатору")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = MediaTypeDTO.class)))
    @GetMapping("/{id}")
    public ResponseEntity<?> getMediaTypeById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(service.getMediaTypeById(id), HttpStatus.OK);
    }

    @ApiCreate
    @Operation(summary = "Создать медиа-тип")
    @ApiResponse(responseCode = "201", description = " случае успешного создания сущности",
            content = @Content(schema = @Schema(implementation = MediaTypeDTO.class)))
    @PostMapping
    public ResponseEntity<?> createMediaType(
            @Parameter(description = "Product to add cannot null or empty.", required = true, schema = @Schema(implementation = MediaTypeSaveDTO.class))
            @Valid
            @RequestBody MediaTypeSaveDTO mediaTypeSaveDTO
    ) {
        return new ResponseEntity<>(service.createMediaType(mediaTypeSaveDTO), HttpStatus.CREATED);
    }

    @ApiUpdate
    @Operation(summary = "Обновить медиа-тип")
    @ApiResponse(responseCode = "200", description = "В случае успешного выполнения",
            content = @Content(schema = @Schema(implementation = MediaTypeDTO.class)))
    @PutMapping
    public ResponseEntity<?> updateMediaType(@RequestBody MediaTypeDTO mediaTypeDTO) {
        return new ResponseEntity<>(service.updateMediaType(mediaTypeDTO), HttpStatus.OK);
    }

    @ApiDelete
    @Operation(summary = "Удалить медиа-тип")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMediaType(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(service.deleteMediaTypeById(id), HttpStatus.NO_CONTENT);
    }

}
