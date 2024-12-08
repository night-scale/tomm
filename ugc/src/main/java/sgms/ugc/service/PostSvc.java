package sgms.ugc.service;

import sgms.ugc.model.Post;
import sgms.ugc.model.PostLike;
import sgms.ugc.repository.PostLikeRepo;
import sgms.ugc.repository.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class PostSvc {

    private final PostRepo postRepo;
    private final PostLikeRepo postLikeRepo;

    @Autowired
    public PostSvc(PostRepo postRepo, PostLikeRepo postLikeRepo) {
        this.postRepo = postRepo;
        this.postLikeRepo = postLikeRepo;
    }

    public boolean storePost(Post post) {
        try {
            postRepo.save(post);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deletePost(Long id) {
        try{
            // TODO jwt校验
            postRepo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public long count() {
        try{
            return postRepo.count();
        } catch (Exception e) {
            return -1;
        }
    }

    public List<Post> getAllById(List<Long> ids){
        try{
            return postRepo.findAllByIdIn(ids);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean like(PostLike pl){
        try{
            postLikeRepo.save(pl);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean dislike(long userId, long postId){
        try{
            postLikeRepo.deleteLike(userId, postId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
