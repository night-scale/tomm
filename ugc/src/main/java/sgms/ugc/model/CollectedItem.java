package sgms.ugc.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class CollectedItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public CollectedItem(User user, Content content, Collection collection) {
        this.creator = user;
        this.content = content;
        this.collection = collection;
    }

    @OneToOne
    @JoinColumn(name = "content_id")
    private Content content;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    @CreatedDate
    @Column(name = "collected_at", updatable = false)
    private LocalDateTime collectedAt;
}
