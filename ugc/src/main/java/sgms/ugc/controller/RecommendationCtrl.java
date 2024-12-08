package sgms.ugc.controller;

import sgms.ugc.dto.ApiResponse;
import sgms.ugc.model.Post;
import sgms.ugc.model.Video;
import sgms.ugc.service.PostSvc;
import sgms.ugc.service.VideoSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/content")
public class RecommendationCtrl {
    private final PostSvc postSvc;
    private final VideoSvc videoSvc;

    @Autowired
    public RecommendationCtrl(PostSvc postSvc, VideoSvc videoSvc) {
        this.postSvc = postSvc;
        this.videoSvc = videoSvc;
    }

    @GetMapping("/post")
    public ApiResponse<List<Post>> fetchPost(){
        //推荐系统暂时没有实现，在post数量范围内生成10个随机数
        long count = postSvc.count();
        List<Long> li = gen10Long(count);
        List<Post> postList = postSvc.getAllById(li);
        return ApiResponse.ok(postList);
    }

    @GetMapping("/video")
    public ApiResponse<List<Video>> fetchVideo(){
        long count = videoSvc.count();
        List<Long> li = gen10Long(count);
        List<Video> videoList = videoSvc.getAllById(li);
        return ApiResponse.ok(videoList);
    }

    private List<Long> gen10Long(long count){
        Random random = new Random();
        List<Long> ret = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            long randomNumber = random.nextLong(count + 1);
            ret.add(randomNumber);
        }
        return ret;
    }
}
