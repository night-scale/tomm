package sgms.ugc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.service.FollowSvc;

@RestController
@RequestMapping("/api/follow")
public class FollowCtrl {

    private final FollowSvc followSvc;

    public FollowCtrl(FollowSvc followSvc) {
        this.followSvc = followSvc;
    }

    // 关注用户
    @PostMapping("/follow")
    public ApiResponse<Object> followUser(@RequestParam Long toUserId) {
        return followSvc.followUser(toUserId);
    }

    // 取消关注
    @PostMapping("/unfollow")
    public ApiResponse<Object> unfollowUser(@RequestParam Long toUserId) {
        return followSvc.unfollowUser(toUserId);
    }
}
