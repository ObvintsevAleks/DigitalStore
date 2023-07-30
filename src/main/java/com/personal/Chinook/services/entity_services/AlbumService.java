package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.Album;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Track;
import com.personal.Chinook.repositories.IRepositoryAlbum;
import com.personal.Chinook.repositories.IRepositoryArtist;
import com.personal.Chinook.repositories.IRepositoryTrack;
import com.personal.Chinook.services.common_query_functions.INameQuery;
import com.personal.Chinook.services.common_query_functions.IDBCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("AlbumService")
public class AlbumService implements IDBCrud<Album, AlbumDTO> , INameQuery<Album> {

    private final IRepositoryAlbum repoAlbum;
    private final IRepositoryArtist repoArtist;
    private final IRepositoryTrack repoTrack;

    @Autowired
    public AlbumService(
            @Qualifier("AlbumRepo") IRepositoryAlbum albumRepo,
            @Qualifier("ArtistRepo") IRepositoryArtist artistRepo,
            @Qualifier("TrackRepo") IRepositoryTrack trackRepo
    ) {
        repoAlbum = albumRepo;
        repoArtist = artistRepo;
        repoTrack = trackRepo;
    }

    @Override
    public List<Album> getAll() {

        return repoAlbum.findAll();
    }

    @Override
    public Optional<Album> getById(Integer id) {
        if (id == null)
            throw new InvalidFieldException("ERROR, found empty field");

        if (id < 0)
            throw new InvalidFieldException("ERROR, ID cannot be negative");

        return repoAlbum.findById(id);
    }

    @Override
    public Album persist(AlbumDTO albumDTO) {
        if (
                albumDTO.getId() == null ||
                albumDTO.getTitle() == null ||
                albumDTO.getTitle().isEmpty() ||
                albumDTO.getTitle().isBlank() ||
                albumDTO.getArtistId() == null
        )
            throw new InvalidFieldException("ERROR, found empty fields on request");

        if (albumDTO.getId() < 0)
            throw new InvalidFieldException("ERROR, album ID cannot be negative");

        //objects used for the assigned persisting of related entities
        Artist assignedArtist;
        List<Track> assignedTrackList = new ArrayList<>();

        //artist existence validation
        if (!repoArtist.existsById(albumDTO.getArtistId()))
            throw new NotFoundInDBException("ERROR, album artist not found in database");

        //retrieves values from non-empty-guaranteed Optional instance after possible exception handling
        assignedArtist = repoArtist.findById(albumDTO.getArtistId()).get();

        //track existence validation
        if (albumDTO.getTrackList() != null && !albumDTO.getTrackList().isEmpty()) {
            for (Integer trackId : albumDTO.getTrackList()) {
                if (!repoTrack.existsById(trackId))
                    throw new NotFoundInDBException("ERROR, track not found in database");

                Track assignedTrack = repoTrack.findById(trackId).get();
                assignedTrackList.add(assignedTrack);
            }
        }
        //track list from DTO ID list is assigned, be it empty or containing data, based on previous logic flow

        return repoAlbum.save(
                new Album(
                        albumDTO.getId(),
                        albumDTO.getTitle(),
                        assignedArtist,
                        assignedTrackList
                )
        );
    }

    @Override
    public void update(AlbumDTO albumDTO) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public List<Album> getByName(String albumName) {
        return null;
    }
}
