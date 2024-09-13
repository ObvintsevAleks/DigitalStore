package com.personal.Chinook.models;

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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MediaType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MediaTypeId", nullable = false)
    private UUID id;

    @Column(name = "Name", nullable = false, unique = true)
    private String name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "createdAt", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

}
