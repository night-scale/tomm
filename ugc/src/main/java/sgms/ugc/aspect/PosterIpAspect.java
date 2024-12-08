package sgms.ugc.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpServletRequest;
import sgms.ugc.anno.PosterIp;

@Aspect
@Component
public class PosterIpAspect {

    private static final ThreadLocal<String> clientIpHolder = new ThreadLocal<>();

    @Around("@annotation(PosterIp)")
    public Object logClientIp(ProceedingJoinPoint joinPoint, PosterIp posterIp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String ip = getPosterIp(request);
        clientIpHolder.set(ip);

        try {
            return joinPoint.proceed();
        } finally {
            clientIpHolder.remove();
        }
    }

    private String getPosterIp(HttpServletRequest request) {
        String clientIp;
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            clientIp = xForwardedFor.split(",")[0].trim();
        } else {
            clientIp = request.getRemoteAddr();
        }
        return clientIp;
    }

    public static String getPosterIpFromHolder() {
        return clientIpHolder.get();
    }
}
