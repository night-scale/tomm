package sgms.ugc.service;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Service;
import sgms.ugc.dto.CollectedItemReq;
import sgms.ugc.model.CollectedItem;
import sgms.ugc.model.Collection;
import sgms.ugc.model.Content;
import sgms.ugc.model.User;
import sgms.ugc.repository.CollectedItemRepo;
import sgms.ugc.repository.CollectionRepo;
import sgms.ugc.repository.ContentRepo;
import sgms.ugc.repository.UserRepo;

import java.util.Optional;

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
    public boolean storeCollection(Collection c){
        try {
            collectionRepo.save(c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 删除收藏夹
    public boolean deleteCollection(long id){
        try {
            collectionRepo.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // 创建一个收藏
    //TODO 更好的判空方式
    public boolean storeItem(CollectedItemReq i){
        try {
            Optional<User> u = userRepo.findById(StpUtil.getLoginIdAsLong());
            Optional<Content> content = contentRepo.findById(i.collectionId());
            Optional<Collection> collection = collectionRepo.findById(i.collectionId());

            if(u.isPresent() && content.isPresent() && collection.isPresent()){
                CollectedItem item = new CollectedItem(u.get(), content.get(), collection.get());
                collectedItemRepo.save(item);
                return true;
            }else{
                //TODO 急需优雅方式
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // 删除某个收藏
    public boolean deleteItem(long id){
        try {
            Optional<CollectedItem> item = collectedItemRepo.findById(id);
            if(item.isPresent()){
                CollectedItem present = item.get();
                if(present.getCreator().getId() != StpUtil.getLoginIdAsLong()){
                    return false;
                }else{
                    collectionRepo.deleteById(id);
                    return true;
                }
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
}
