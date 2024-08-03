package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.ArtistDTO;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.mapper.ArtistMapper;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistRepository artistRepository;
    private final ArtistMapper artistMapper;


    @Transactional
    public ArtistDTO createArtist(ArtistDTO artistDTO) {
        Artist artist = artistMapper.toArtist(artistDTO);
        artistRepository.save(artist);
        return artistMapper.toArtistDTO(artist);
    }


    @Transactional(readOnly = true)
    public ArtistDTO getArtistById(Integer id) throws NotFoundInDBException {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new NotFoundInDBException(""));
        return artistMapper.toArtistDTO(artist);
    }

    @Transactional
    public ArtistDTO updateArtist(Integer id, ArtistDTO artistDTO) throws NotFoundInDBException {
        Artist artistEntity = artistRepository.findById(id).orElseThrow(() -> new NotFoundInDBException(""));
        if (artistMapper.toArtistDTO(artistEntity).equals(artistDTO)) {
            return artistMapper.toArtistDTO(artistEntity);
        }
        artistMapper.updateArtist(artistEntity, artistDTO);
        artistRepository.save(artistEntity);
        return artistMapper.toArtistDTO(artistEntity);
    }

    @Transactional
    public ArtistDTO deleteArtistById(Integer id) throws NotFoundInDBException {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new NotFoundInDBException("asrd"));
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
