package sgms.ugc.dto;

public record CreateCollectionReq(
        String name,
        String description,
        Boolean visibility
) { }
