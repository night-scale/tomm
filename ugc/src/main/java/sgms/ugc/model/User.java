package sgms.ugc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sgms.ugc.enums.AccountStatus;
import sgms.ugc.enums.Gender;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

    @Column(length = 64, nullable = false)
    private String password;

    @Size(max = 16)
    @Column(nullable = false)
    private String salt;

    @Column(unique = true, nullable = false, length = 15)
    private String tel;

    @Column(length = 255, nullable = true)
    private String email;

    @Column(nullable = true)
    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(length = 50, nullable = true)
    private String slogan;

    @Column(length = 255, nullable = true)
    private String avatar;

    @Column(length = 255, nullable = true)
    private String cover;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    // 管理OneToMany关系
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Collection> collections;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    List<CollectedItem> collectedItems;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Content> contents;

    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Comment> comments;
    /////////////////////////////////////


    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;
}