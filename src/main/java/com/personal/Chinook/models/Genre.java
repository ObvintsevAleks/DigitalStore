package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.personal.Chinook.models.enumpack.GenreDirection;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "GenreId", nullable = false)
    private UUID id;

    @Column(name = "Name", nullable = false)
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    @Column(name = "GenreDirection", nullable = false)
    @Enumerated(EnumType.STRING)
    private GenreDirection genreDirection;

}
