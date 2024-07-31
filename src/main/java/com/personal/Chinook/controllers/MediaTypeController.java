package com.personal.Chinook.controllers;

import com.personal.Chinook.DTO.MediaTypeDTO;
import com.personal.Chinook.models.MediaType;
import com.personal.Chinook.services.entity_services.MediaTypeService;
import com.personal.Chinook.utils.swagger.ApiCreate;
import com.personal.Chinook.utils.swagger.ApiDelete;
import com.personal.Chinook.utils.swagger.ApiGet;
import com.personal.Chinook.utils.swagger.ApiUpdate;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "MediaType controller")
@RestController
@RequestMapping("/mediatypes")
public class MediaTypeController {

    private final MediaTypeService service;

    @Autowired
    public MediaTypeController(@Qualifier("MediaTypeService") MediaTypeService service) {
        this.service = service;
    }

    @ApiGet
    @GetMapping("/all")
    public ResponseEntity<?> getAllMediaTypes() {
        List<MediaType> responseList = service.getAll();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchid/{mediaTypeId}")
    public ResponseEntity<?> getMediaTypeById(@PathVariable("mediaTypeId") Integer id) {
        Optional<MediaType> responseMediaType = service.getById(id);
        return new ResponseEntity<>(responseMediaType, HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/searchname/{mediaTypeName}")
    public ResponseEntity<?> getMediaTypeByName(@PathVariable("mediaTypeName") String name) {
        List<MediaType> responseList = service.getByName(name);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ApiGet
    @GetMapping("/allcount")
    public ResponseEntity<?> getMediaTypeCount() {
        return new ResponseEntity<>(service.getAll().size(), HttpStatus.OK);
    }

    @ApiCreate
    @PostMapping("/create")
    public ResponseEntity<?> createMediaType(@RequestBody MediaTypeDTO mediaTypeDTO) {
        MediaType responseMediaType = service.persist(mediaTypeDTO);
        return new ResponseEntity<>(responseMediaType, HttpStatus.CREATED);
    }

    @ApiUpdate
    @PutMapping("/update")
    public ResponseEntity<?> updateMediaType(@RequestBody MediaTypeDTO mediaTypeDTO) {
        service.update(mediaTypeDTO);
        return new ResponseEntity<>("The Media Type has been successfully updated!", HttpStatus.OK);
    }

    @ApiDelete
    @DeleteMapping("/remove/{mediaTypeId}")
    public ResponseEntity<?> deleteMediaType(@PathVariable("mediaTypeId") Integer id) {
        service.deleteById(id);
        return new ResponseEntity<>("The Media Type was successfully deleted!", HttpStatus.NO_CONTENT);
    }
}
