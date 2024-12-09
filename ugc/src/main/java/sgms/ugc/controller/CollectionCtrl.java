package sgms.ugc.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CreateCollectionReq;
import sgms.ugc.service.CollectionSvc;

@RestController
@RequestMapping("/collect")
public class CollectionCtrl {
    private final CollectionSvc collectionSvc;

    public CollectionCtrl(CollectionSvc collectionSvc) {
        this.collectionSvc = collectionSvc;
    }

    // 添加/更新 收藏夹
    @PostMapping("/create")
    public ApiResponse<Object> createCollection(@RequestBody @Validated CreateCollectionReq req){
        return collectionSvc.createCollection(req);
    }

    // 删除收藏夹
    @PostMapping("/delete")
    public ApiResponse<Object> deleteCollection(@RequestBody Long id){
        return collectionSvc.deleteCollection(id);
    }

}
