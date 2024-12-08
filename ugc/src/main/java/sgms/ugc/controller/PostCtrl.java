package sgms.ugc.controller;

import cn.dev33.satoken.stp.StpUtil;
import sgms.ugc.anno.PosterIp;
import sgms.ugc.aspect.PosterIpAspect;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.model.Post;
import sgms.ugc.model.PostLike;
import sgms.ugc.util.GeoIpUtil;
import sgms.ugc.service.PostSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostCtrl {

    private final PostSvc postSvc;
    @Autowired
    public PostCtrl(PostSvc postSvc) {
        this.postSvc = postSvc;
    }

    @PosterIp
    @PostMapping("/upload")
    public ApiResponse<String> uploadPost(@RequestBody Post post) {
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        long id = StpUtil.getLoginIdAsLong();
        if(post.getId() != id){
            return ApiResponse.error(7, "post user don't match");
        }

        String ip = PosterIpAspect.getPosterIpFromHolder();
        post.setLocation(GeoIpUtil.getLocationByIp(ip));

        boolean isSuccess = postSvc.storePost(post);
        if (isSuccess) {
            return ApiResponse.ok("success");
        } else {
            return ApiResponse.error(1, "server storage process failure");
        }
    }

    @PostMapping("/delete")
    public ApiResponse<String> deletePost(@RequestBody Long postId) {
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean isSuccess = postSvc.deletePost(postId);
        if (isSuccess) {
            return ApiResponse.ok("success");
        } else {
            return ApiResponse.error(1, "server storage process failure");
        }
    }

    @PostMapping("/like")
    public ApiResponse<String> likePost(@RequestBody PostLike postLike){
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean success = postSvc.like(postLike);
        if(success){
            return ApiResponse.ok();
        }
        return ApiResponse.error(3, "inner database error");
    }

    @PostMapping("/dislike")
    public ApiResponse<String> dislikePost(@RequestParam long id, @RequestParam long postId){
        //TODO 可以用sa token提取id了，下一步怎么快速进行userId和请求携带的userId的校验
        boolean success = postSvc.dislike(id, postId);
        if(success){
            return ApiResponse.ok();
        }
        return ApiResponse.error(3, "inner database error");
    }

    @PostMapping("/collect")
    public ApiResponse<String> collectPost(){
        return ApiResponse.ok();
    }

    @PostMapping("/undo-collect")
    public ApiResponse<String> undoCollectPost(){
        return ApiResponse.ok();
    }


}
