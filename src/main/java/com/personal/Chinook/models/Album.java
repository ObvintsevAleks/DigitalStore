package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.Chinook.models.enumpack.AlbumType;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "album")
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "album_id", nullable = false)
    private UUID id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "album_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AlbumType albumType;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private ZonedDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "artist_id",
            referencedColumnName = "artist_id",
            foreignKey = @ForeignKey(name = "fk_album_artist_id"),
            nullable = false
    )
    private Artist artist;

}
