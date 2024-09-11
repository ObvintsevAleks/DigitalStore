package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "invoiceLines")
@EqualsAndHashCode(exclude = "invoiceLines")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TrackId", nullable = false)
    private UUID id;

    @Column(name = "Name", nullable = false)
    private String name;

    @Column(name = "Author")
    private String author;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    private ZonedDateTime createdAt;

    @Column(name = "milliseconds")
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

}
