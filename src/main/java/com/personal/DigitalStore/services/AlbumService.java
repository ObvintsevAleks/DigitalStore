package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.AlbumDTO;
import com.personal.DigitalStore.dto.AlbumSaveDto;
import com.personal.DigitalStore.dto.ArtistDTO;
import com.personal.DigitalStore.exceptions.custom.InvalidFieldException;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.AlbumMapper;
import com.personal.DigitalStore.models.Album;
import com.personal.DigitalStore.models.Track;
import com.personal.DigitalStore.repositories.AlbumRepository;
import com.personal.DigitalStore.repositories.TrackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumRepository albumRepository;
    private final AlbumMapper albumMapper;

    @Autowired
    private final ArtistService artistService;
    @Autowired
    private final TrackRepository trackRepository;

    @Transactional(readOnly = true)
    public AlbumDTO getAlbumById(UUID id) throws NotFoundInDBException {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден Альбом по id = "+ id));
        return albumMapper.toAlbumDTO(album);
    }

    @Transactional
    public AlbumDTO createAlbum(AlbumSaveDto albumSaveDto) {
        Album album = albumMapper.toAlbum(albumSaveDto);
        albumRepository.save(album);
        return albumMapper.toAlbumDTO(album);
    }

    @Transactional
    public AlbumDTO updateAlbum(AlbumDTO albumDTO) throws NotFoundInDBException {
        Album albumEntity = albumRepository.findById(albumDTO.getId()).orElseThrow(() -> new NotFoundInDBException("Не найден Альбом по id = "+ albumDTO.getId()));
        if (albumMapper.toAlbumDTO(albumEntity).equals(albumDTO)) {
            return albumMapper.toAlbumDTO(albumEntity);
        }
        albumMapper.updateAlbum(albumEntity, albumDTO);
        albumRepository.save(albumEntity);
        return albumMapper.toAlbumDTO(albumEntity);
    }

    @Transactional
    public AlbumDTO deleteAlbumById(UUID id) throws NotFoundInDBException {
        Album album = albumRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден Альбом по id = "+ id));
        List<Track> tracks = trackRepository.searchByAlbumId(id);
        if(!tracks.isEmpty()) {
            throw new InvalidFieldException("Перед тем как удалить Альбом c id ["+ id +"], необходимо удалить связанные с ним треки "
                    +"\n" + tracks.stream().map(Track::getId).toList());
        }
        albumRepository.deleteById(id);
        return albumMapper.toAlbumDTO(album);
    }

    @Transactional(readOnly = true)
    public List<AlbumDTO> getAllAlbumsByArtistId(UUID id) throws NotFoundInDBException {
        List<Album> albums = albumRepository.searchByArtistId(id).orElseThrow(() -> new NotFoundInDBException("Не найдены альбомы по id артиста = "+ id));
        return albumMapper.toAlbumDTOs(albums);
    }

    @Transactional(readOnly = true)
    public List<AlbumDTO> getAlbumsByTitle(String title) throws NotFoundInDBException {
        List<Album> albums = albumRepository.searchByTitle(title).orElseThrow(() -> new NotFoundInDBException("Не найдены альбомы по заголовку = "+ title));
        return albumMapper.toAlbumDTOs(albums);
    }

    @Transactional(readOnly = true)
    public List<AlbumDTO> getAlbumsByArtistPseudonym(String pseudonym) throws NotFoundInDBException {
        List<ArtistDTO> artists = artistService.getArtistsByPseudonym(pseudonym);
        List<AlbumDTO> albums = new ArrayList<>();
        for (ArtistDTO artist : artists) {
            UUID artistId = artist.getId();
            albums.addAll(getAllAlbumsByArtistId(artistId));
        }
        return albums;
    }

}
