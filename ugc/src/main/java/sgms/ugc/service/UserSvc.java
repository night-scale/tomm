package sgms.ugc.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.transaction.annotation.Transactional;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CreateUserWithTelPwReq;
import sgms.ugc.enums.BusinessErrorCode;
import sgms.ugc.model.User;
import sgms.ugc.repository.UserRepo;
import org.springframework.stereotype.Service;

import sgms.ugc.enums.AccountStatus;
import sgms.ugc.util.PasswordUtil;

@Service
public class UserSvc {
    private final UserRepo userRepo;

    public UserSvc(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Transactional
    public ApiResponse<String> createWithTelPassword(CreateUserWithTelPwReq req) {
        String randomUsername = generateRandomUsername();
        String randomNickname = generateRandomNickname();

        String salt = PasswordUtil.generateSalt();
        String encryptedPassword = PasswordUtil.encryptPassword(req.password(), salt);

        User newUser = new User();
        newUser.setUsername(randomUsername);
        newUser.setNickname(randomNickname);
        newUser.setPassword(encryptedPassword);
        newUser.setTel(req.tel());
        newUser.setBirthday(req.birthday());
        newUser.setGender(req.gender());
        newUser.setStatus(AccountStatus.NORMAL);
        newUser.setSalt(salt);

        userRepo.save(newUser);

        return ApiResponse.ok();
    }

    private String generateRandomUsername() {
        return "user" + System.currentTimeMillis();
    }

    private String generateRandomNickname() {
        return "用户" + System.currentTimeMillis();
    }

    @Transactional
    public ApiResponse<String> passwordLogin(String identifier, String password) {
        // 查找用户（根据手机号或者邮箱）
        User user = userRepo.findByTel(identifier);
        if (user == null) {
            user = userRepo.findByEmail(identifier);
        }

        if (user == null) {
            return ApiResponse.error(BusinessErrorCode.RESOURCE_NOT_EXIST);
        }

        String storedSalt = user.getSalt();
        String storedPasswordHash = user.getPassword();

        StpUtil.login(user.getId());
        String token = StpUtil.getTokenValueByLoginId(user.getId());

        return ApiResponse.ok();
    }

    @Transactional
    public ApiResponse<Object> deleteUser() {
        Long id = StpUtil.getLoginIdAsLong();
        userRepo.deleteById(id);
        StpUtil.logout();
        //TODO 注销账号需要手机号验证
        return ApiResponse.ok();
    }
}
