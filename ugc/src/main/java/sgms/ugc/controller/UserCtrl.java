package sgms.ugc.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestBody;
import sgms.ugc.dto.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/signup")
    public ApiResponse<String> register(@RequestBody User u){
        //TODO 设置默认账号状态
        String rawPw =  u.getPassword();
        String hashedPassword = DigestUtils.sha256Hex(rawPw);
        u.setPassword(hashedPassword);

        boolean res = userSvc.add(u);
        if(!res){
            return ApiResponse.error(4, "failed");
        }
        return ApiResponse.ok();
    }

    @PostMapping("/login")
    public ApiResponse<String> login(@RequestBody LoginReq loginReq){
        long res = userSvc.passwordLogin(loginReq.telOrEmail(), loginReq.password());
        if(res >= 0){
            StpUtil.login(res);//创建token并储存在cookie中
            //TODO 成功登录返回信息
            return ApiResponse.ok();
        } else if(res == -1){
            return ApiResponse.error(5, "not exists");
        } else if (res == -2) {
            return ApiResponse.error(6, "wrong password");
        } else {
            return ApiResponse.error(1, "internal database error");
        }
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
