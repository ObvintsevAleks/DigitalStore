package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.DTO.ArtistSaveDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.ArtistMapper;
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
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final ArtistMapper artistMapper;

    @Transactional
    public ArtistDTO createArtist(ArtistSaveDTO artistSaveDTO) {
        Artist artist = artistMapper.toArtist(artistSaveDTO);
        artistRepository.save(artist);
        return artistMapper.toArtistDTO(artist);
    }

    @Transactional(readOnly = true)
    public ArtistDTO getArtistById(UUID id) throws NotFoundInDBException {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден Артист по id = "+ id));
        return artistMapper.toArtistDTO(artist);
    }

    @Transactional
    public ArtistDTO updateArtist(ArtistDTO artistDTO) throws NotFoundInDBException {
        Artist artistEntity = artistRepository.findById(artistDTO.getId()).orElseThrow(() ->  new NotFoundInDBException("Не найден Артист по id = "+ artistDTO.getId()));
        if (artistMapper.toArtistDTO(artistEntity).equals(artistDTO)) {
            return artistMapper.toArtistDTO(artistEntity);
        }
        artistMapper.updateArtist(artistEntity, artistDTO);
        artistRepository.save(artistEntity);
        return artistMapper.toArtistDTO(artistEntity);
    }

    @Transactional
    public ArtistDTO deleteArtistById(UUID id) throws NotFoundInDBException {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("Не найден Артист по id = "+ id));
        UUID artistId = artist.getId();
        List<Album> albums = albumRepository.searchByArtistId(artistId);
        if(!albums.isEmpty()) {
            throw new InvalidFieldException("Перед тем как удалить Артиста c id ["+ id +"], необходимо удалить связанные с ним альбомы "
                    +"\n" + albums.stream().map(Album::getId).toList());
        }
        artistRepository.deleteById(id);
        return artistMapper.toArtistDTO(artist);
    }

    @Transactional(readOnly = true)
    public List<ArtistDTO> getArtistsByName(String name) throws NotFoundInDBException {
        List<Artist> artists = artistRepository.searchByName(name);
        if (artists.isEmpty()) {
            throw new NotFoundInDBException("2");
        }
        return artistMapper.toArtistDTOs(artists);
    }

}
