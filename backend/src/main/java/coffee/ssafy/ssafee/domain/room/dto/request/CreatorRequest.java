package coffee.ssafy.ssafee.domain.room.dto.request;

public record CreatorRequest(
        String name,
        String bank,
        String account,
        String mattermostWebhookUrl
) {
}
