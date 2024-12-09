package sgms.ugc.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sgms.ugc.anno.PosterIp;
import sgms.ugc.aspect.PosterIpAspect;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CollectedItemReq;
import sgms.ugc.model.Content;
import sgms.ugc.model.Like;
import sgms.ugc.service.CollectionSvc;
import sgms.ugc.service.ContentSvc;
import sgms.ugc.util.AesUtil;
import sgms.ugc.util.GeoIpUtil;

@RestController
@RequestMapping("/content")
public class ContentCtrl {

    private final ContentSvc contentSvc;
    private final CollectionSvc collectionSvc;
    @Autowired
    public ContentCtrl(ContentSvc contentSvc, CollectionSvc collectionSvc) {
        this.contentSvc = contentSvc;
        this.collectionSvc = collectionSvc;
    }

    @PosterIp
    @PostMapping("/upload")
    public ApiResponse<String> uploadContent(@RequestBody Content content) {
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        long id = StpUtil.getLoginIdAsLong();

        content.setAuthorId(id);

        String ip = PosterIpAspect.getPosterIpFromHolder();
        content.setLocation(GeoIpUtil.getLocationByIp(ip));

        boolean isSuccess = contentSvc.storeContent(content);
        if (isSuccess) {
            return ApiResponse.ok("success");
        } else {
            return ApiResponse.error(1, "server storage process failure");
        }
    }

    @PostMapping("/delete")
    public ApiResponse<String> deletePost(@RequestBody Long id) {
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean isSuccess = contentSvc.deleteContent(id);
        if (isSuccess) {
            return ApiResponse.ok("success");
        } else {
            return ApiResponse.error(1, "server storage process failure");
        }
    }

    @PostMapping("/like")
    public ApiResponse<String> likeContent(@RequestBody Like like){
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean success = contentSvc.storeLike(like);
        if(success){
            return ApiResponse.ok();
        }
        return ApiResponse.error(3, "inner database error");
    }

    @PostMapping("/dislike")
    public ApiResponse<String> dislikeContent(@RequestParam Long id, @RequestParam long postId){
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean success = contentSvc.deleteLike(id, postId);
        if(success){
            return ApiResponse.ok();
        }
        return ApiResponse.error(3, "inner database error");
    }

    // 收藏某个内容 到收藏夹
    //TODO 加密函数可能抛出异常
    @SneakyThrows
    @PostMapping("/collect")
    public ApiResponse<String> collectContent(@RequestBody CollectedItemReq i) {
        boolean res = collectionSvc.storeItem(i);
        if (res) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error();
        }
    }

    // 不再收藏某个内容
    //TODO 加密函数可能抛出异常
    @SneakyThrows
    @PostMapping("/undo-collect")
    public ApiResponse<String> undoCollect(@RequestParam String encryptedId){
        long id = Long.parseLong(AesUtil.decryptWithKey(encryptedId));

        boolean res = collectionSvc.deleteItem(id);

        if (res) {
            return ApiResponse.ok();
        } else {
            return ApiResponse.error();
        }
    }


}