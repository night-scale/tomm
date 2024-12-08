package sgms.ugc.dto;

public record LoginReq(
    String telOrEmail,
    String password
) {}
