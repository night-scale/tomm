package sgms.ugc.util;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.util.Arrays;

public class PasswordUtil {

    private static final int SALT_SIZE = 16;
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256;

    // 生成随机盐
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_SIZE];
        random.nextBytes(salt);
        return Base64.encodeBase64String(salt); // Base64编码盐
    }

    // 使用PBKDF2对密码加密
    public static String encryptPassword(String password, String salt) {
        byte[] saltBytes = Base64.decodeBase64(salt);
        byte[] passwordBytes = password.getBytes();
        byte[] hash = pbkdf2(passwordBytes, saltBytes, ITERATIONS, KEY_LENGTH);
        return Base64.encodeBase64String(hash); // 将加密后的密码转换为Base64字符串
    }

    // PBKDF2算法
    private static byte[] pbkdf2(byte[] password, byte[] salt, int iterations, int keyLength) {
        PBEParametersGenerator generator = new PBEParametersGenerator() {
            @Override
            public void init(byte[] password, byte[] salt, int iterationCount) {
                super.init(password, salt, iterationCount);
            }

            @Override
            public CipherParameters generateDerivedParameters(int i) {
                return null;
            }

            @Override
            public CipherParameters generateDerivedParameters(int i, int i1) {
                return null;
            }

            @Override
            public CipherParameters generateDerivedMacParameters(int i) {
                return null;
            }
        };
        generator.init(password, salt, iterations);
        byte[] key = new byte[keyLength / 8]; // 生成目标长度的密钥
        generator.generateDerivedMacParameters(key.length * 8);
        return Arrays.copyOf(key, key.length);
    }

    // 校验密码
    public static boolean verifyPassword(String inputPassword, String storedPasswordHash, String storedSalt) {
        String inputPasswordHash = encryptPassword(inputPassword, storedSalt);
        return storedPasswordHash.equals(inputPasswordHash); // 验证密码
    }
}

