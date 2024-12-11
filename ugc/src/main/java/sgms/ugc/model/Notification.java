package sgms.ugc.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import sgms.ugc.enums.NotificationType;

import java.time.LocalDateTime;
@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    @Column(length = 300)
    private String content;

    @Column(nullable = false)
    private User receiver;

    private String url;

    @Column(nullable = false)
    private Boolean isRead = false;

    @Column(nullable = false)
    private Boolean status;

    @CreatedDate
    @Column(updatable = false, nullable = false)
    private LocalDateTime createdAt;
}
