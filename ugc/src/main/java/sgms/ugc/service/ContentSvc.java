package sgms.ugc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sgms.ugc.model.Content;
import sgms.ugc.model.Like;
import sgms.ugc.repository.ContentRepo;
import sgms.ugc.repository.LikeRepo;

import java.util.List;

@Service
public class ContentSvc {
    private final ContentRepo contentRepo;
    private final LikeRepo likeRepo;
    @Autowired
    public ContentSvc(ContentRepo contentRepo, LikeRepo likeRepo) {
        this.contentRepo = contentRepo;
        this.likeRepo = likeRepo;
    }

    public boolean storeContent(Content c) {
        try {
            contentRepo.save(c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteContent(Long id) {
        try{
            // TODO jwt校验
            contentRepo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public long count() {
        try{
            return contentRepo.count();
        } catch (Exception e) {
            return -1;
        }
    }

    public List<Content> getAllById(List<Long> ids){
        try{
            return contentRepo.findAllByIdIn(ids);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean storeLike(Like l){
        try{
            likeRepo.save(l);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteLike(long userId, long postId){
        try{
            likeRepo.deleteLike(userId, postId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
