package sgms.ugc.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import sgms.ugc.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgms.ugc.dto.CreateUserWithTelPw;
import sgms.ugc.dto.LoginReq;
import sgms.ugc.model.User;
import sgms.ugc.service.UserSvc;

@RestController
@RequestMapping("account")
public class UserCtrl {
    private final UserSvc userSvc;

    public UserCtrl(UserSvc userSvc) {
        this.userSvc = userSvc;
    }

    @PostMapping("/telpw")
    public ApiResponse<String> register(@RequestBody CreateUserWithTelPw req){
        return userSvc.createWithTelPassword(req);
    }

    //TODO 请求体中明文传输真的没问题吗
    @PostMapping("/pwlogin")
    public ApiResponse<String> passwordLogin(@RequestBody LoginReq loginReq){
        return userSvc.passwordLogin(loginReq.telOrEmail(), loginReq.password());
    }

    @PostMapping("/logout")
    public ApiResponse<String> logout(){
        StpUtil.logout();
        return ApiResponse.ok();
    }

    @PostMapping("/delete")
    public ApiResponse<String> deleteAccount(){
        //TODO 删除/注销账号
        return ApiResponse.ok();
    }
}
