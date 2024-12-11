package sgms.ugc.dto;

import lombok.Setter;
import sgms.ugc.enums.NotificationType;
import sgms.ugc.model.Notification;

@Setter
public class ClientNotification {
    private NotificationType type;
    private String content;

}
