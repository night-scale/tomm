package sgms.ugc.model;

import lombok.Data;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Comment parent;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id", nullable = false)
    private Content content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    public Comment() {}

    public Comment(String text, String img, String location, User user, Content content, Comment replyTo) {
        this.location = location;
        this.parent = replyTo;
        this.img = img;
        this.text = text;
        this.creator = user;
        this.content = content;
    }
}
