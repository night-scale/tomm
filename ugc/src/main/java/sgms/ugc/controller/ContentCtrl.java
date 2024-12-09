package sgms.ugc.controller;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sgms.ugc.anno.PosterIp;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CreateCollectedItemReq;
import sgms.ugc.dto.CreateContentReq;
import sgms.ugc.service.CollectionSvc;
import sgms.ugc.service.ContentSvc;
import sgms.ugc.util.AesUtil;

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
    public ApiResponse<Object> uploadContent(@RequestBody @Validated CreateContentReq req) {
        return contentSvc.createContent(req);
    }

    @PostMapping("/delete")
    public ApiResponse<Object> deletePost(@RequestBody Long id) {
        return contentSvc.deleteContent(id);
    }

    @PostMapping("/like")
    public ApiResponse<Object> likeContent(@RequestBody Long contentId){
        return contentSvc.createLike(contentId);
    }

    @PostMapping("/dislike")
    public ApiResponse<Object> dislikeContent(@RequestParam Long contentId){
        return contentSvc.deleteLike(contentId);
    }

    @PostMapping("/collect")
    public ApiResponse<Object> collectContent(@RequestBody CreateCollectedItemReq req) {
        return collectionSvc.createCollectedItem(req);
    }

    //TODO 加密函数可能抛出异常
    @SneakyThrows
    @PostMapping("/undo-collect")
    public ApiResponse<Object> undoCollect(@RequestParam String encryptedId){
        long id = Long.parseLong(AesUtil.decryptWithKey(encryptedId));
        return collectionSvc.deleteCollectedItem(id);
    }


}