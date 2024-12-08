package sgms.ugc.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sgms.ugc.enums.AccountStatus;
import sgms.ugc.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 14)
    private String username;

    @Column(nullable = false, length = 16)
    private String nickname;

    @Column(length = 64)
    private String password;

    @Column(unique = true, nullable = false, length = 15)
    private String tel;

    @Column(length = 255)
    private String email;

    @Column(columnDefinition = "DATE")
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(length = 50)
    private String slogan;

    @Column(length = 255)
    private String avatar;

    @Column(length = 255)
    private String cover;

    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}