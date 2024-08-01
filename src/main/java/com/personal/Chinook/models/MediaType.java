package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@ToString(exclude = "tracks")
@EqualsAndHashCode(exclude = "tracks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaType {

    @Id
    @Column(name = "MediaTypeId", nullable = false)
    private Integer id;

    @Column(name = "Name", length = 120)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "mediaType", fetch = FetchType.LAZY)
    private List<Track> tracks;
}
