package com.personal.Chinook.services.entity_services;

import com.personal.Chinook.DTO.AlbumDTO;
import com.personal.Chinook.exceptions.custom.InvalidFieldException;
import com.personal.Chinook.exceptions.custom.NotFoundInDBException;
import com.personal.Chinook.models.Album;
import com.personal.Chinook.models.Artist;
import com.personal.Chinook.models.Track;
import com.personal.Chinook.repositories.IRepositoryAlbum;
import com.personal.Chinook.repositories.ArtistRepository;
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
public class AlbumService {




}
