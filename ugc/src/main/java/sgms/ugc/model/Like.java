package sgms.ugc.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(
        name = "likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "content_id"})
)
public class Like {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "content_id")
    private Long contentId;
}
