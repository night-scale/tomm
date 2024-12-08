package sgms.ugc.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.json.JSONObject;

@Service
public class GeoIpUtil {
    public static String getLocationByIp(String ip) {
        try {
            String accessKey = System.getenv("IPSTACK_KEY"); // 确保已经设置环境变量
            if (accessKey == null || accessKey.isEmpty()) {
                return "IPSTACK_KEY 环境变量未设置";
            }

            JSONObject jsonResponse = getJsonObject(ip, accessKey);

            return jsonResponse.optString("region_name", "未知");
        } catch (Exception e) {
            return "位置解析失败";
        }
    }
    public static String extractClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("X-Real-IP");
        if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
    private static JSONObject getJsonObject(String ip, String accessKey) throws IOException {
        String urlString = "http://api.ipstack.com/" + ip + "?access_key=" + accessKey;
        URL url = null;
        try {
            url = new URI(urlString).toURL();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }
}

