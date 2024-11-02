package com.personal.DigitalStore.models;

import com.personal.DigitalStore.models.enumpack.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "user_name", unique = true)
    private String username;

    private String password;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole userRole;

}
