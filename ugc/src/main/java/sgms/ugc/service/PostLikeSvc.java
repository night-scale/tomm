package sgms.ugc.service;

import sgms.ugc.model.PostLike;
import sgms.ugc.repository.PostLikeRepo;
import org.springframework.stereotype.Service;

@Service
public class PostLikeSvc {
    private final PostLikeRepo postLikeRepo;

    public PostLikeSvc(PostLikeRepo postLikeRepo) {
        this.postLikeRepo = postLikeRepo;
    }

    public boolean like(PostLike pl){
        try{
            postLikeRepo.save(pl);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean cancel(long userId, long postId){
        try{
            postLikeRepo.deleteLike(userId, postId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
