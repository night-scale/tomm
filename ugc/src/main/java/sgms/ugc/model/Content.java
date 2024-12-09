package sgms.ugc.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import sgms.ugc.enums.ContentType;

import java.time.LocalDateTime;

//TODO 内容类别决定了不同的文件类型，需要进行验证

@Data
@Entity(name = "contents")
@EntityListeners(AuditingEntityListener.class)
public class Content {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ContentType type;

    @Column(nullable = false)
    private String url;

    @ManyToOne()
    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(name = "created_at", updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
}