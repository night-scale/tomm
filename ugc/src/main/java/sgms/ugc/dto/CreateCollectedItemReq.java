package sgms.ugc.dto;

public record CreateCollectedItemReq(
        Long contentId,
        Long collectionId
) { }
