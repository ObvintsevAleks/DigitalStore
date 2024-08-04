package com.personal.Chinook.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "tracks")
@EqualsAndHashCode(exclude = "tracks")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MediaTypeId", nullable = false)
    private UUID id;

    @Column(name = "Name", length = 120)
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    private ZonedDateTime createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "mediaType", fetch = FetchType.LAZY)
    private List<Track> tracks;
}
