package sgms.ugc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 25, nullable = false)
    @Size(max = 10)
    private String name;

    @Column(length = 25, nullable = false)
    @Size(max = 25)
    private String description;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    private Boolean visibility = false; // 默认不公开

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    public Collection(){}
    public Collection(Long id, String name, String description, Boolean visibility){
        this.name = name;
        this.description = description;
        this.visibility = visibility;
    }
}