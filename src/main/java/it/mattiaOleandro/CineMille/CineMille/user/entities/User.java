package it.mattiaOleandro.CineMille.CineMille.user.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    private String surname;

    private boolean isActive = false;

    @Column(length = 36)
    private String activationCode;

    @Column(length = 36)
    private String passwordResetCode;

    @Column(unique = true)
    private String email;
    private String password;

    // https://jwt.io/ allows you to decode, verify and generate JWT.
    private LocalDateTime jwtCreatedOn;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLES",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            })
    private Set<Role> roles;

    @Column(unique = true)
    private String documentNumber;

    @Column(unique = true)
    private String badgeNumber;
}
