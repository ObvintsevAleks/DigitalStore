package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "track")
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TrackId", nullable = false)
    private UUID id;

    @Column(name = "Name", nullable = false, length = 200)
    private String name;


    @Column(name = "Author", length = 220)
    private String author;

    @NotNull
    private Integer milliseconds;

    @Column(name = "Bytes")
    private Integer bytes;

    @Column(name = "UnitPrice", nullable = false, precision = 10, scale = 2)
    private BigDecimal unitPrice;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "AlbumId",
            referencedColumnName = "AlbumId",
            foreignKey = @ForeignKey(name = "FK_TrackAlbumId"),
            nullable = false
    )
    private Album album;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "GenreId",
            referencedColumnName = "GenreId",
            foreignKey = @ForeignKey(name = "FK_TrackGenreId"),
            nullable = false
    )
    private Genre genre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "MediaTypeId",
            referencedColumnName = "MediaTypeId",
            foreignKey = @ForeignKey(name = "FK_TrackMediaTypeId"),
            nullable = false
    )
    private MediaType mediaType;

    @JsonIgnore
    @ManyToMany(
            fetch = FetchType.LAZY,
            mappedBy = "trackList"
    )
    private List<Playlist> playlistList;

    @JsonIgnore
    @OneToMany(mappedBy = "track", fetch = FetchType.LAZY)
    private List<InvoiceLine> invoiceLineList;
}
