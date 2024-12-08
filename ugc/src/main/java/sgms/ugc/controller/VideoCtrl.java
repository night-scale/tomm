package sgms.ugc.controller;

import sgms.ugc.aspect.PosterIpAspect;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.model.Video;
import sgms.ugc.model.VideoLike;
import sgms.ugc.service.VideoSvc;
import sgms.ugc.util.GeoIpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
public class VideoCtrl {

    @Autowired
    private VideoSvc videoSvc;

    @PostMapping("/upload")
    public ApiResponse<String> uploadVideo(@RequestBody Video video) {
        // TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验

        String ip = PosterIpAspect.getPosterIpFromHolder();
        video.setLocation(GeoIpUtil.getLocationByIp(ip));

        boolean isSuccess = videoSvc.storeVideo(video);
        if (isSuccess) {
            return ApiResponse.ok("success");
        } else {
            return ApiResponse.error(1, "server storage process failure");
        }
    }

    @PostMapping("/delete")
    public ApiResponse<String> deleteVideo(@RequestBody Long postId) {
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean isSuccess = videoSvc.deleteVideo(postId);
        if (isSuccess) {
            return ApiResponse.ok("success");
        } else {
            return ApiResponse.error(1,"server storage process failure");
        }
    }

    @PostMapping("/like")
    public ApiResponse<String> likeVideo(@RequestBody VideoLike postLike){
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean success = videoSvc.like(postLike);
        if(success){
            return ApiResponse.ok();
        }
        return ApiResponse.error(3, "inner database error");
    }

    @PostMapping("/dislike")
    public ApiResponse<String> dislikeVideo(@RequestParam long id, @RequestParam long videoId){
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean success = videoSvc.dislike(id, videoId);
        if(success){
            return ApiResponse.ok();
        }
        return ApiResponse.error(3, "inner database error");
    }

    @PostMapping("/collect")
    public ApiResponse<String> collectVideo(){
        return ApiResponse.ok();
    }

    @PostMapping("/uncollect")
    public ApiResponse<String> undoCollectVideo(){
        return ApiResponse.ok();
    }

}