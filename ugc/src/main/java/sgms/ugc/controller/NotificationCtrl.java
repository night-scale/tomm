package sgms.ugc.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.service.NotificationSvc;


@RestController
@RequestMapping("/notifications")
public class NotificationCtrl {
    private final NotificationSvc notificationSvc;

    public NotificationCtrl(NotificationSvc notificationSvc) {
        this.notificationSvc = notificationSvc;
    }

    @GetMapping("/subscribe")
    public ApiResponse<SseEmitter> subscribe(){
        return notificationSvc.subscribe();
    }

}