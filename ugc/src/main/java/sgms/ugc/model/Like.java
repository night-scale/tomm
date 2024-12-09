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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User creator;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Content content;

    public Like(User user, Content content) {
        this.creator = user;
        this.content = content;
    }
}
