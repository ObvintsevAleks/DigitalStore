package com.personal.DigitalStore.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@ToString(exclude = "albums")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = "albums")
@Table(name = "artist")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "artist_id", nullable = false)
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @Column(name = "pseudonym", nullable = false)
    private String pseudonym;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "birth_date", nullable = false)
    @CreatedDate
    private LocalDate birthDate;

    // jsonignore property to not display it as part of request/response body
    // relationship left untouched for jpql benefit for queries
    @JsonIgnore
    @OneToMany(mappedBy = "artist", fetch = FetchType.LAZY)
    private List<Album> albums;
}
