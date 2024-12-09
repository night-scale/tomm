package sgms.ugc.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CollectionReq;
import sgms.ugc.model.Collection;
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
    public ApiResponse<String> createCollection(@RequestBody CollectionReq nc){
        //TODO 判空
        Collection collection = new Collection(
                StpUtil.getLoginIdAsLong(),
                nc.name(),
                nc.description(),
                nc.visibility()
        );
        boolean res = collectionSvc.storeCollection(collection);
        if(res){
            return ApiResponse.ok();
        } else {
            return ApiResponse.error();
        }
    }

    // 删除收藏夹
    @PostMapping("/delete")
    public ApiResponse<String> deleteCollection(@RequestBody Long id){
        boolean res = collectionSvc.deleteCollection(id);
        if(res){
            return ApiResponse.ok();
        } else {
            return ApiResponse.error();
        }
    }

}
