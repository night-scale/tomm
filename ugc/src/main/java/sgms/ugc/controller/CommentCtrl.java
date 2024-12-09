package sgms.ugc.controller;

import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import sgms.ugc.dto.ApiResponse;
import sgms.ugc.dto.CommentReq;
import sgms.ugc.model.Comment;
import sgms.ugc.service.CommentSvc;

@RestController
@RequestMapping("/comment")
public class CommentCtrl {
    private final CommentSvc commentSvc;

    public CommentCtrl(CommentSvc commentSvc) {
        this.commentSvc = commentSvc;
    }

    @PostMapping("/add")
    public ApiResponse<Object> comment(@RequestBody @Validated CommentReq c){
        return commentSvc.createComment(c);
    }

    @PostMapping("/withdraw")
    public ApiResponse<Object> withdrawComment(@RequestBody @Validated Long id){
        return commentSvc.deleteComment(id);
    }
    //TODO 评论排序方式
    @GetMapping("/list")
    public ApiResponse<Page<Comment>> getComments(
            @RequestParam Long contentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "false") boolean timeOrder
    ) {
        return commentSvc.getPagedComments(contentId, page, size, timeOrder);
    }

    //
    @GetMapping("/sub")
    public ApiResponse<Page<Comment>> getSubComments(
            @RequestParam Long parentId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return commentSvc.getPagedSubComments(parentId, page, size);
    }
}
