package sgms.ugc.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CreateCollectedItemReq;
import sgms.ugc.dto.CreateCollectionReq;
import sgms.ugc.enums.BusinessErrorCode;
import sgms.ugc.exception.BusinessException;
import sgms.ugc.model.CollectedItem;
import sgms.ugc.model.Collection;
import sgms.ugc.model.Content;
import sgms.ugc.model.User;
import sgms.ugc.repository.CollectedItemRepo;
import sgms.ugc.repository.CollectionRepo;
import sgms.ugc.repository.ContentRepo;
import sgms.ugc.repository.UserRepo;


@Service
public class CollectionSvc {
    private final CollectionRepo collectionRepo;
    private final CollectedItemRepo collectedItemRepo;
    private final UserRepo userRepo;
    private final ContentRepo contentRepo;

    public CollectionSvc(CollectionRepo collectionRepo, CollectedItemRepo collectedItemRepo, UserRepo userRepo, ContentRepo contentRepo) {
        this.collectionRepo = collectionRepo;
        this.collectedItemRepo = collectedItemRepo;
        this.userRepo = userRepo;
        this.contentRepo = contentRepo;
    }

    // 新增收藏夹
    @Transactional
    public ApiResponse<Object> createCollection(CreateCollectionReq req){
        Long userId = StpUtil.getLoginIdAsLong();

        User u = userRepo.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.RESOURCE_NOT_EXIST));

        Collection collection = new Collection(
                u,
                req.name(),
                req.description(),
                req.visibility()
        );

        collectionRepo.save(collection);

        return ApiResponse.ok();
    }

    // 删除收藏夹
    @Transactional
    public ApiResponse<Object> deleteCollection(Long id){
        Long userId = StpUtil.getLoginIdAsLong();
        int res = collectionRepo.deleteByIdAndCreator(id, userId);
        if(res == 0){
            //TODO 老问题，删除出现错误有两种情况
            return ApiResponse.error(BusinessErrorCode.RESOURCE_NOT_EXIST);
        }
        return ApiResponse.ok();  // 返回成功响应
    }

    // 创建一个收藏
    @Transactional
    public ApiResponse<Object> createCollectedItem(CreateCollectedItemReq req){
        Long userId = StpUtil.getLoginIdAsLong();

        User u = userRepo.findById(userId)
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.RESOURCE_NOT_EXIST));

        Collection c = collectionRepo.findById(req.collectionId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.RESOURCE_NOT_EXIST));
        Content content = contentRepo.findById(req.contentId())
                .orElseThrow(() -> new BusinessException(BusinessErrorCode.CONTENT_NOT_EXIST));

        collectedItemRepo.save(new CollectedItem(u, content, c));
        return ApiResponse.ok();
    }

    // 删除某个收藏
    @Transactional
    public ApiResponse<Object> deleteCollectedItem(Long id){
        Long userId = StpUtil.getLoginIdAsLong();
        int res = collectedItemRepo.deleteByIdAndCreator_Id(id, userId);
        if(res == 0){
            //TODO 老问题，删除出现错误有两种情况
            return ApiResponse.error(BusinessErrorCode.RESOURCE_NOT_EXIST);
        }
        return ApiResponse.ok();
    }
}
