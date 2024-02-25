package coffee.ssafy.ssafee.domain.shop.dto.request;

public record ShopRequest(
        String name,
        String address,
        String phone,
        Boolean enabledOrder,
        Integer minimumPrice,
        Boolean closed
) {
}
