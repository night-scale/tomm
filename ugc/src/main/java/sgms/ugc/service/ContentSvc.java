package sgms.ugc.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgms.ugc.aspect.PosterIpAspect;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CreateContentReq;
import sgms.ugc.enums.BusinessErrorCode;
import sgms.ugc.exception.BusinessException;
import sgms.ugc.model.Content;
import sgms.ugc.model.Like;
import sgms.ugc.model.User;
import sgms.ugc.repository.ContentRepo;
import sgms.ugc.repository.LikeRepo;
import sgms.ugc.repository.UserRepo;
import sgms.ugc.util.GeoIpUtil;

import java.util.List;

@Service
public class ContentSvc {
    private final ContentRepo contentRepo;
    private final LikeRepo likeRepo;
    private final UserRepo userRepo;
    @Autowired
    public ContentSvc(ContentRepo contentRepo, LikeRepo likeRepo, UserRepo userRepo) {
        this.contentRepo = contentRepo;
        this.likeRepo = likeRepo;
        this.userRepo = userRepo;
    }

    public ApiResponse<Object> createContent(CreateContentReq req) {
        Content c;
        Long userId = StpUtil.getLoginIdAsLong();
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.RESOURCE_NOT_EXIST));
        //TODO你好，怎么把网络请求做成异步的
        String location = GeoIpUtil.getLocationByIp(PosterIpAspect.getPosterIpFromHolder());
        c = new Content(
                req.type(),
                req.url(),
                u,
                location,
                req.title(),
                req.description()
        );

        contentRepo.save(c);
        return ApiResponse.ok();
    }

    public ApiResponse<Object> deleteContent(Long id) {
        Long userId = StpUtil.getLoginIdAsLong();
        contentRepo.deleteByIdAndAuthor_Id(id, userId);
        return ApiResponse.ok();
    }

    public ApiResponse<Object> createLike(Long contentId){
        if(contentId == null)
            return ApiResponse.error(BusinessErrorCode.PARAM_NOT_VALID);
        Long userId = StpUtil.getLoginIdAsLong();

        Content c = contentRepo.findById(contentId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.CONTENT_NOT_EXIST));
        User u = userRepo.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.RESOURCE_NOT_EXIST));

        likeRepo.save(new Like(u, c));
        return ApiResponse.ok();
    }

    public ApiResponse<Object> deleteLike(Long contentId){
        Long userId = StpUtil.getLoginIdAsLong();
        int res = likeRepo.deleteLikeByContent_IdAndCreator_Id(contentId, userId);
        //TODO 实际上：不是该用户点赞  内容不存在
        if(res == 0){
            return ApiResponse.error(BusinessErrorCode.RESOURCE_NOT_EXIST);
        }
        return ApiResponse.ok();
    }
}
