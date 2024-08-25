package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.DTO.MediaTypeSaveDTO;
import com.personal.Chinook.services.entity_services.MediaTypeService;
import com.personal.Chinook.utils.swagger.ApiCreate;
import com.personal.Chinook.utils.swagger.ApiDelete;
import com.personal.Chinook.utils.swagger.ApiGet;
import com.personal.Chinook.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Tag(name = "mediaTypes")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mediatypes")
public class MediaTypeController {

    private final MediaTypeService service;

    @ApiGet
    @GetMapping
    public ResponseEntity<?> getAllMediaTypes() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/{id}")
    public ResponseEntity<?> getMediaTypeById(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(service.getMediaTypeById(id), HttpStatus.OK);
    }

    @ApiCreate
    @PostMapping
    public ResponseEntity<?> createMediaType(@RequestBody MediaTypeSaveDTO mediaTypeSaveDTO) {
        return new ResponseEntity<>(service.createMediaType(mediaTypeSaveDTO), HttpStatus.CREATED);
    }

    @ApiUpdate
    @PutMapping
    public ResponseEntity<?> updateMediaType(@RequestBody MediaTypeDTO mediaTypeDTO) {
        return new ResponseEntity<>(service.updateMediaType(mediaTypeDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMediaType(@PathVariable("id") UUID id) {
        return new ResponseEntity<>(service.deleteMediaTypeById(id), HttpStatus.NO_CONTENT);
    }

}
