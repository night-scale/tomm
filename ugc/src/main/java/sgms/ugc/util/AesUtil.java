package sgms.ugc.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AesUtil {
    // TODO密钥解决方案 密钥派生函数

    // 定义加密算法
    private static final String AES_ALGORITHM = "AES";
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA256";

    // 生成随机盐值
    private static final SecureRandom random = new SecureRandom();

    // 生成安全的 AES 密钥，派生自密码
    public static SecretKey deriveKey(String password) throws Exception {
        byte[] salt = new byte[16]; // 盐值长度
        random.nextBytes(salt);  // 生成盐
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 256); // 256 位密钥
        SecretKeyFactory factory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
        SecretKey secretKey = factory.generateSecret(spec);
        return secretKey;
    }

    // 使用 AES 加密数据
    public static String encrypt(String data, String password) throws Exception {
        SecretKey key = deriveKey(password);  // 派生密钥
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);  // 使用 Base64 编码返回加密数据
    }

    // 使用 AES 解密数据
    public static String decrypt(String encryptedData, String password) throws Exception {
        SecretKey key = deriveKey(password);  // 派生密钥
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));  // 解码 Base64 数据并解密
        return new String(decryptedData);  // 返回解密后的原始数据
    }

    // 使用指定的密钥加密（直接传递密钥）
    public static String encryptWithKey(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);  // 使用 Base64 编码返回加密数据
    }

    // 使用指定的密钥解密（直接传递密钥）
    public static String decryptWithKey(String encryptedData, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));  // 解码 Base64 数据并解密
        return new String(decryptedData);  // 返回解密后的原始数据
    }
    // 使用密钥加密
    public static String encryptWithKey(String data) throws Exception {
        String key = "1234567812345678";  // 密钥，16字节
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);

        byte[] encryptedData = cipher.doFinal(data.getBytes());
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    // 使用密钥解密
    public static String decryptWithKey(String encryptedData) throws Exception {
        String key = "1234567812345678";  // 密钥，16字节
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);

        byte[] decodedData = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedData = cipher.doFinal(decodedData);

        return new String(decryptedData);
    }

    // 生成一个新的 AES 密钥
    public static SecretKey generateNewKey() throws Exception {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGenerator.init(256);  // 256 位密钥
        return keyGenerator.generateKey();
    }
}

