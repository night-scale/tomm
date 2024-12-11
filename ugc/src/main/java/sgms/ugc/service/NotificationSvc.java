package sgms.ugc.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.ClientNotification;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class NotificationSvc {

    private final RedisTemplate<String, SseEmitter> redisTemplate;

    private final CopyOnWriteArrayList<SseEmitter> clients = new CopyOnWriteArrayList<>();

    public NotificationSvc(RedisTemplate<String, SseEmitter> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ApiResponse<SseEmitter> subscribe() {
        String userId = StpUtil.getLoginIdAsString();
        SseEmitter emitter = new SseEmitter();

        emitter.onCompletion(() -> {
            clients.remove(emitter);
            redisTemplate.opsForValue().getOperations().delete(userId);
        });
        emitter.onTimeout(() -> {
            clients.remove(emitter);
            redisTemplate.opsForValue().getOperations().delete(userId);
        });

        redisTemplate.opsForValue().set(userId, emitter);

        clients.add(emitter);

        //TODO注册之后需要返回emitter吗
        return ApiResponse.ok(emitter);
    }

    public boolean sendNotificationToUser(String userId, ClientNotification message) {
        SseEmitter emitter = redisTemplate.opsForValue().get(userId);
        if (emitter != null) {
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
                return true;
            } catch (IOException e) {
                redisTemplate.opsForValue().getOperations().delete(userId);
                return false;
            }
        } else {
            return false;
        }
    }

    public void sendNotificationToAll(ClientNotification message) {
        for (SseEmitter emitter : clients) {
            try {
                emitter.send(message, MediaType.APPLICATION_JSON);
            } catch (IOException e) {
                clients.remove(emitter);
            }
        }
    }
}
