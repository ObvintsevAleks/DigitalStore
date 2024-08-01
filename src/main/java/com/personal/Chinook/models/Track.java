package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TrackId", nullable = false)
    private Integer id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;


    @Column(name = "Composer", length = 220)
    private String composer;

    @NotNull
    private Integer milliseconds;

    @Column(name = "Bytes")
    private Integer bytes;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;


    @JsonIgnore
    @OneToMany(mappedBy = "track", fetch = FetchType.LAZY)
    private List<InvoiceLine> invoiceLineList;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "AlbumId",
            referencedColumnName = "AlbumId",
            foreignKey = @ForeignKey(name = "FK_TrackAlbumId")
    )
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "GenreId",
            referencedColumnName = "GenreId",
            foreignKey = @ForeignKey(name = "FK_TrackGenreId")
    )
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "MediaTypeId",
            referencedColumnName = "MediaTypeId",
            foreignKey = @ForeignKey(name = "FK_TrackMediaTypeId")
    )
    private MediaType mediaType;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "trackList"
    )
    private List<Playlist> playlistList;
}
