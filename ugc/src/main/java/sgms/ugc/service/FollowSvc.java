package sgms.ugc.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.enums.BusinessErrorCode;
import sgms.ugc.exception.BusinessException;
import sgms.ugc.model.Follow;
import sgms.ugc.model.User;
import sgms.ugc.repository.FollowRepo;
import sgms.ugc.repository.UserRepo;

@Service
public class FollowSvc {

    private final UserRepo userRepo;

    private final FollowRepo followRepo;

    public FollowSvc(UserRepo userRepo, FollowRepo followRepo) {
        this.userRepo = userRepo;
        this.followRepo = followRepo;
    }

    // 添加关注
    public ApiResponse<Object> followUser(Long toUserId) {
        Long fromUserId = StpUtil.getLoginIdAsLong();

        User fromUser = userRepo.findById(fromUserId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.RESOURCE_NOT_EXIST));
        User toUser = userRepo.findById(toUserId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.RESOURCE_NOT_EXIST));

        Follow follow = new Follow();
        follow.setFromUser(fromUser);
        follow.setToUser(toUser);
        followRepo.save(follow);

        return ApiResponse.ok();
    }

    // 取消关注
    public ApiResponse<Object> unfollowUser(Long toUserId) {
        Long fromUserId = StpUtil.getLoginIdAsLong();

        int res = followRepo.deleteByFromUser_IdAndToUser_Id(fromUserId, toUserId);
        if(res == 0){
            return ApiResponse.error(BusinessErrorCode.NOT_OWNER);
        }

        return ApiResponse.ok();
    }
}

