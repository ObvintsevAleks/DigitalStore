package com.personal.DigitalStore.services;

import com.personal.DigitalStore.dto.ArtistDTO;
import com.personal.DigitalStore.dto.ArtistSaveDTO;
import com.personal.DigitalStore.exceptions.custom.InvalidFieldException;
import com.personal.DigitalStore.exceptions.custom.NotFoundInDBException;
import com.personal.DigitalStore.mappers.ArtistMapper;
import com.personal.DigitalStore.models.Album;
import com.personal.DigitalStore.models.Artist;
import com.personal.DigitalStore.repositories.AlbumRepository;
import com.personal.DigitalStore.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;

    @Autowired
    private final AlbumRepository albumRepository;

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
        List<Album> albums = albumRepository.searchByArtistId(artistId).orElseThrow(() -> new NotFoundInDBException("Не найден альбом по id артиста = "+ id));
        if(!albums.isEmpty()) {
            throw new InvalidFieldException("Перед тем как удалить Артиста c id ["+ id +"], необходимо удалить связанные с ним альбомы "
                    +"\n" + albums.stream().map(Album::getId).toList());
        }
        artistRepository.deleteById(id);
        return artistMapper.toArtistDTO(artist);
    }

    @Transactional(readOnly = true)
    public List<ArtistDTO> getArtistsByName(String name) throws NotFoundInDBException {
        List<Artist> artists = artistRepository.searchByName(name).orElseThrow(() -> new NotFoundInDBException("Не найдена артист по имени = "+ name));
        return artistMapper.toArtistDTOs(artists);
    }

    @Transactional(readOnly = true)
    public List<ArtistDTO> getArtistsByPseudonym(String pseudonym) throws NotFoundInDBException {
        List<Artist> artists = artistRepository.searchByPseudonym(pseudonym).orElseThrow(() -> new NotFoundInDBException("Не найдена артист по псевдониму = "+ pseudonym));
        return artistMapper.toArtistDTOs(artists);
    }

}
