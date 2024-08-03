package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.MediaTypeDTO;
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

@Tag(name = "MediaType controller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/mediatypes")
public class MediaTypeController {

    private final MediaTypeService service;

    @ApiGet
    @GetMapping("/all")
    public ResponseEntity<?> getAllMediaTypes() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchid/{mediaTypeId}")
    public ResponseEntity<?> getMediaTypeById(@PathVariable("mediaTypeId") Integer id) {
        return new ResponseEntity<>(service.getMediaTypeById(id), HttpStatus.OK);
    }


    @ApiCreate
    @PostMapping("/create")
    public ResponseEntity<?> createMediaType(@RequestBody MediaTypeDTO mediaTypeDTO) {
        return new ResponseEntity<>(service.createMediaType(mediaTypeDTO), HttpStatus.CREATED);
    }

    @ApiUpdate
    @PutMapping("/update/{mediaTypeId}")
    public ResponseEntity<?> updateMediaType(@PathVariable("mediaTypeId") Integer id,
                                             @RequestBody MediaTypeDTO mediaTypeDTO) {
        return new ResponseEntity<>(service.updateMediaType(id, mediaTypeDTO), HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{mediaTypeId}")
    public ResponseEntity<?> deleteMediaType(@PathVariable("mediaTypeId") Integer id) {
        return new ResponseEntity<>(service.deleteMediaTypeById(id), HttpStatus.NO_CONTENT);
    }
}
