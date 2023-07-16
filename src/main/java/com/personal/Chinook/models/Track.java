package com.personal.Chinook.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Track {

    @Id
    @Column(name = "TrackId", nullable = false)
    private Integer trackId;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;

    @Column(name = "Milliseconds", nullable = false)
    private Integer milliseconds;

    @Column(name = "Bytes")
    private Integer bytes;

    @Column(name = "UnitPrice", nullable = false)
    private BigDecimal unitPrice;

    @OneToMany(mappedBy = "track", fetch = FetchType.LAZY)
    private List<InvoiceLine> invoiceLineList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AlbumId", referencedColumnName = "AlbumId", foreignKey = @ForeignKey(name = "FK_TrackAlbumId"))
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "GenreId", referencedColumnName = "GenreId", foreignKey = @ForeignKey(name = "FK_TrackGenreId"))
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MediTypeId", referencedColumnName = "MediaTypeId", foreignKey = @ForeignKey(name = "FK_TrackMediaTypeId"))
    private MediaType mediaType;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "trackList")
    private List<Playlist> playlistList;
}
