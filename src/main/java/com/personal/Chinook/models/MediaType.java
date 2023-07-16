package com.personal.Chinook.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class MediaType {

    @Id
    @Column(name = "MediaTypeId", nullable = false)
    private Integer mediaTypeId;

    @Column(name = "Name", length = 120)
    private String name;

    @OneToMany(mappedBy = "mediaType", fetch = FetchType.LAZY)
    private List<Track> trackList;
}
