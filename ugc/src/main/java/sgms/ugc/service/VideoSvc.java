package sgms.ugc.service;

import sgms.ugc.model.Video;
import sgms.ugc.model.VideoLike;
import sgms.ugc.repository.VideoLikeRepo;
import sgms.ugc.repository.VideoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoSvc {
    private final VideoRepo videoRepo;
    private final VideoLikeRepo videoLikeRepo;

    @Autowired
    public VideoSvc(VideoRepo videoRepo, VideoLikeRepo videoLikeRepo) {
        this.videoRepo = videoRepo;
        this.videoLikeRepo = videoLikeRepo;
    }

    public boolean storeVideo(Video video) {
        try {
            videoRepo.save(video);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteVideo(Long id) {
        try{
            // TODO jwt校验
            videoRepo.deleteById(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public long count(){
        try{
            return videoRepo.count();
        } catch (Exception e) {
            return -1;
        }
    }

    public List<Video> getAllById(List<Long> li) {
        try{
            return videoRepo.findAllByIdIn(li);
        } catch (Exception e) {
            return null;
        }
    }

    public boolean like(VideoLike vl){
        try{
            videoLikeRepo.save(vl);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public boolean dislike(long userId, long postId){
        try{
            videoLikeRepo.deleteLike(userId, postId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
