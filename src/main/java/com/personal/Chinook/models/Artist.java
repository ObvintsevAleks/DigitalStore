package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(exclude = "albumList")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "albumList")
public class Artist {

    @Id
    @Column(name = "ArtistId", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 120)
    private String name;

    // jsonignore property to not display it as part of request/response body
    // relationship left untouched for jpql benefit for queries
    @JsonIgnore
    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Album> albumList;
}
