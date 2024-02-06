package coffee.ssafy.ssafee.domain.shop.dto.request;

public record MenuRequest(
        String name,
        String description,
        Integer price,
        Boolean soldout
) {
}
