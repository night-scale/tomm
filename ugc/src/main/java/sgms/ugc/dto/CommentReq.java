package sgms.ugc.dto;

import jakarta.validation.constraints.NotNull;

public record CommentReq(
        String img,
        Long replyTo,

        @NotNull
        String text,
        @NotNull
        Long contentId
) { }
