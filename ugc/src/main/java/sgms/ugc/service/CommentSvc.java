package sgms.ugc.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import sgms.ugc.aspect.PosterIpAspect;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CommentReq;
import sgms.ugc.enums.BusinessErrorCode;
import sgms.ugc.exception.ResourceNotExistE;
import sgms.ugc.model.Comment;
import sgms.ugc.model.Content;
import sgms.ugc.model.User;
import sgms.ugc.repository.CommentRepo;
import sgms.ugc.repository.ContentRepo;
import sgms.ugc.repository.UserRepo;
import sgms.ugc.util.GeoIpUtil;

import java.util.List;

@Service
public class CommentSvc {
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;
    private final ContentRepo contentRepo;
    public CommentSvc(CommentRepo commentRepo, UserRepo userRepo, ContentRepo contentRepo) {
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
        this.contentRepo = contentRepo;
    }

    public ApiResponse<Object> createComment(CommentReq req) {
        if (req.text() == null || req.contentId() == null || req.img() == null) {
            return ApiResponse.error(BusinessErrorCode.EMPTY_FIELD);
        }

        User user = userRepo.findById((Long) StpUtil.getLoginId())
                .orElseThrow(() -> new ResourceNotExistE(BusinessErrorCode.RESOURCE_NOT_EXIST));
        Content content = contentRepo.findById(req.contentId())
                .orElseThrow(() -> new ResourceNotExistE(BusinessErrorCode.CONTENT_NOT_EXIST));

        String location = GeoIpUtil.getLocationByIp(PosterIpAspect.getPosterIpFromHolder());

        Comment comment;
        if(req.replyTo() != null){
            Comment parent = commentRepo.findById(req.replyTo())
                    .orElseThrow(() -> new ResourceNotExistE(BusinessErrorCode.RESOURCE_NOT_EXIST));
            comment = new Comment(req.text(), req.img(), location, user, content, parent);
        }else {
            comment = new Comment(req.text(), req.img(), location, user, content, null);
        }

        commentRepo.save(comment);
        return ApiResponse.ok();
    }

    public ApiResponse<Object> deleteComment(Long id){
        if(id == null){
            return ApiResponse.error(BusinessErrorCode.PARAM_NOT_VALID);
        }
        Long userId = StpUtil.getLoginIdAsLong();
        int deletedCount = commentRepo.deleteByIdAndUserId(id, userId);

        if (deletedCount == 0) {
            return ApiResponse.error(BusinessErrorCode.NOT_OWNER);
        }

        return ApiResponse.ok();
    }

    public ApiResponse<Page<Comment>> getPagedComments(Long id, int page, int size, boolean timeOrder){
        if(id == null){
            return ApiResponse.error(BusinessErrorCode.PARAM_NOT_VALID);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt"))); // 按照评论时间倒序排列
        return ApiResponse.ok(commentRepo.findByContent(id, pageable));
    }

    public ApiResponse<Page<Comment>> getPagedSubComments(Long id, int page, int size){
        if(id == null){
            return ApiResponse.error(BusinessErrorCode.PARAM_NOT_VALID);
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc("createdAt")));
        return ApiResponse.ok(commentRepo.findByParent(id, pageable));
    }
}
