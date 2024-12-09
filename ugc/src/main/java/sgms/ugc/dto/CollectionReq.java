package sgms.ugc.dto;

public record CollectionReq(
        String name,
        String description,
        Boolean visibility
) { }
