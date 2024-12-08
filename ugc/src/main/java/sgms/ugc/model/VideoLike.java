package sgms.ugc.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "video_likes")
@Table(
        name = "video_likes",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "video_id"})
)
public class VideoLike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "video_id")
    private Long videoId;
}
