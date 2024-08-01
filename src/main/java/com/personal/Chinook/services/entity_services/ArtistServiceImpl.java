package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.mapper.ArtistMapper;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.repositories.ArtistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Override
    @Transactional
    public Artist createArtist(Artist artist) {
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Artist getArtistById(Integer id) {
        return null;
    }


    @Override
    @Transactional
    public Artist updateArtist(Integer artistId, Artist artist) {
        return null;
    }

    @Override
    @Transactional
    public Artist deleteArtist(Integer id) {
        return null;
    }
}
