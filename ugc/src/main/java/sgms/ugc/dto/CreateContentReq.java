package sgms.ugc.dto;

import jakarta.validation.constraints.NotNull;
import sgms.ugc.enums.ContentType;

public record CreateContentReq(
        @NotNull
        ContentType type,

        @NotNull
        String url,

        @NotNull
        String title,

        @NotNull
        String description
) { }
