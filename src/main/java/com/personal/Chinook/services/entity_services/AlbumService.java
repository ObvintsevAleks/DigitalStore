package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.DTO.AlbumSaveDto;
import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.AlbumMapper;
import com.personal.Chinook.models.Album;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.repositories.AlbumRepository;
import com.personal.Chinook.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
//    private final ArtistRepository artistRepository;
    private final AlbumMapper albumMapper;


    @Transactional(readOnly = true)
    public AlbumDTO getAlbumById(UUID id) throws NotFoundInDBException {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NotFoundInDBException(""));
        return albumMapper.toAlbumDTO(album);
    }

//    @Transactional(readOnly = true)
//    public List<AlbumDTO> getAllAlbumsByArtistId(UUID id) throws NotFoundInDBException {
//        List<Album> albums = albumRepository.searchByArtistId(id);
//        return albumMapper.toAlbumDTOs(albums);
//    }

    @Transactional
    public AlbumDTO createAlbum(AlbumSaveDto albumSaveDto) {
        Album album = albumMapper.toAlbum(albumSaveDto);
        albumRepository.save(album);
        return albumMapper.toAlbumDTO(album);
    }

    @Transactional
    public AlbumDTO updateAlbum(AlbumDTO albumDTO) throws NotFoundInDBException {
        Album albumEntity = albumRepository.findById(albumDTO.getId()).orElseThrow(() -> new NotFoundInDBException(""));
        if (albumMapper.toAlbumDTO(albumEntity).equals(albumDTO)) {
            return albumMapper.toAlbumDTO(albumEntity);
        }
        //toDO что делать с id артиста??
        albumMapper.updateAlbum(albumEntity, albumDTO);
        albumRepository.save(albumEntity);
        return albumMapper.toAlbumDTO(albumEntity);
    }

    @Transactional
    public AlbumDTO deleteAlbumById(UUID id) throws NotFoundInDBException {
        Album artist = albumRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("asrd"));
        albumRepository.deleteById(id);
        return albumMapper.toAlbumDTO(artist);
    }

    @Transactional(readOnly = true)
    public List<AlbumDTO> getArtistsByTitle(String title) throws NotFoundInDBException {
        List<Album> albums = albumRepository.searchByTitle(title);
        if (albums.isEmpty()) {
            throw new NotFoundInDBException("2");
        }
        return albumMapper.toAlbumDTOs(albums);
    }


}
